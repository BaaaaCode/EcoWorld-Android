package com.example.ecoworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoworld.databinding.ActivityChatBotBinding

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    private val PREFS_NAME = "EcoWorldPrefs"
    private val KEY_POINTS = "user_points"
    private val KEY_LEVEL = "user_level"

    private var points = 0
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadUserData()

        chatAdapter = ChatAdapter(messages)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatBotActivity)
            adapter = chatAdapter
        }

        binding.btnMission.setOnClickListener {
            startActivity(Intent(this, MissionActivity::class.java))
        }

        // ✅ 메시지 전송 (미션 진행 + 보상 체크)
        binding.btnSend.setOnClickListener {
            val userMessage = binding.editMessage.text.toString()
            if (userMessage.isNotBlank()) {
                addMessage(ChatMessage(userMessage, isUser = true))
                binding.editMessage.text.clear()

                addPoints(1)  // 기본 포인트
                MissionManager.updateMissionProgress("message")
                MissionManager.claimDailyMissionReward { rewardPoints ->
                    addPoints(rewardPoints)
                    Toast.makeText(this, "🎉 일일 미션 클리어! +$rewardPoints pt", Toast.LENGTH_LONG).show()
                }

                val fakeResponse = "테스트 응답: '$userMessage'에 대한 답변입니다."
                addMessage(ChatMessage(fakeResponse, isUser = false))
            }
        }

        // ✅ 사진 업로드 버튼
        binding.btnPhotoUpload.setOnClickListener {
            // TODO: 실제 사진 업로드/촬영 로직 연결
            handlePhotoUploadResponse(aiAccuracy = 92.5)
        }

        updateStatusUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addMessage(message: ChatMessage) {
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
        binding.recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun addPoints(amount: Int) {
        points += amount
        Toast.makeText(this, "포인트 +$amount (총: $points)", Toast.LENGTH_SHORT).show()

        val requiredPoints = 100 * (level * level)
        if (points >= requiredPoints) {
            level++
            Toast.makeText(this, "🎉 레벨 $level 달성!", Toast.LENGTH_LONG).show()
        }

        updateStatusUI()
        saveUserData()
    }

    private fun updateStatusUI() {
        binding.statusText.text = "포인트: $points | 레벨: $level"
        updateCharacterImage(points)
    }

    private fun updateCharacterImage(currentPoints: Int) {
        val resId = when (currentPoints) {
            in 0..499 -> R.drawable.ecoworld_basic
            in 500..999 -> R.drawable.ecoworld_mid
            in 1000..1499 -> R.drawable.ecoworld_end
            else -> R.drawable.ecoworld_main
        }
        binding.characterImage.setImageResource(resId)
    }

    private fun saveUserData() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt(KEY_POINTS, points)
            putInt(KEY_LEVEL, level)
            apply()
        }
    }

    private fun loadUserData() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        points = prefs.getInt(KEY_POINTS, 0)
        level = prefs.getInt(KEY_LEVEL, 1)
    }

    // ✅ 사진 업로드 처리
    private fun handlePhotoUploadResponse(aiAccuracy: Double?) {
        val basePoints = 50
        var totalPoints = basePoints

        if (aiAccuracy != null && aiAccuracy >= 90.0) {
            totalPoints += 20
        }

        addPoints(totalPoints)
        MissionManager.updateMissionProgress("photo_upload")
        MissionManager.claimDailyMissionReward { rewardPoints ->
            addPoints(rewardPoints)
            Toast.makeText(this, "🎉 일일 미션 클리어! +$rewardPoints pt", Toast.LENGTH_LONG).show()
        }

        Toast.makeText(this, "📸 사진 업로드 보상: +$totalPoints pt", Toast.LENGTH_SHORT).show()
    }

    // ✅ 분리배출 처리
    private fun handleClassificationResult(isCorrectlyClassified: Boolean, isCleanState: Boolean) {
        val basePoints = if (isCorrectlyClassified) 100 else 0
        var totalPoints = basePoints

        if (isCorrectlyClassified && isCleanState) {
            totalPoints += 30
        }

        if (basePoints > 0) {
            addPoints(totalPoints)
            MissionManager.updateMissionProgress("classification")
            MissionManager.claimDailyMissionReward { rewardPoints ->
                addPoints(rewardPoints)
                Toast.makeText(this, "🎉 일일 미션 클리어! +$rewardPoints pt", Toast.LENGTH_LONG).show()
            }
            Toast.makeText(this, "✅ 분리배출 성공 보상: +$totalPoints pt", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "❌ 분리배출 실패! 다시 시도하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}
