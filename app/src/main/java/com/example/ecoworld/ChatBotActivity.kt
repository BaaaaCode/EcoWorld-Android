package com.example.ecoworld

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoworld.api.*
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

        // ✅ 뒤로 가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadUserData()

        chatAdapter = ChatAdapter(messages)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatBotActivity)
            adapter = chatAdapter
        }

        binding.btnMission.setOnClickListener {
            val intent = Intent(this, MissionActivity::class.java)
            startActivity(intent)
        }

        binding.btnSend.setOnClickListener {
            val userMessage = binding.editMessage.text.toString()
            if (userMessage.isNotBlank()) {
                addMessage(ChatMessage(userMessage, isUser = true))
                binding.editMessage.text.clear()

                addPoints(1)
                MissionManager.updateMissionProgress("message")

                val fakeResponse = "테스트 응답: '$userMessage'에 대한 답변입니다."
                addMessage(ChatMessage(fakeResponse, isUser = false))
            }
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
        updateCharacterImage()
    }

    private fun updateCharacterImage() {
        val resId = when (level) {
            in 1..3 -> R.drawable.ecoworld_basic
            in 4..7 -> R.drawable.ecoworld_mid
            else -> R.drawable.ecoworld_end
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
}
