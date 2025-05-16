package com.example.ecoworld

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentPoints = 1320  // ✅ 테스트용 포인트 (추후 포인트 적립 로직과 연동 가능)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 상단 정보 표시
        binding.tvTotalPoints.text = "$currentPoints pt"
        binding.tvRecentTimeline.text = "포인트 +50pt, CO₂ 절감 +5kg"

        // ✅ 캐릭터 상태 초기 표시 (onCreate 시 1회 호출)
        updateCharacterImage(currentPoints)

        // ✅ 캐릭터 클릭 시 메시지 출력
        binding.ivCharacter.setOnClickListener {
            binding.tvCharacterMessage.text = "오늘도 분리배출을 실천해요!"
        }

        // ✅ 하단 네비게이션 이벤트 처리
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_chatbot -> {
                    startActivity(Intent(this, ChatBotActivity::class.java))
                    true
                }
                R.id.nav_calendar -> {
                    startActivity(Intent(this, PointTimelineActivity::class.java)) // 캘린더 → 타임라인
                    true
                }
                R.id.nav_points -> {
                    startActivity(Intent(this, MissionActivity::class.java)) // ✅ 포인트 → 미션 화면으로 이동
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // ✅ 홈 화면으로 돌아올 때 캐릭터 상태 최신화
    override fun onResume() {
        super.onResume()
        updateCharacterImage(currentPoints)
    }

    // ✅ 캐릭터 이미지 업데이트 로직 (포인트 기준)
    private fun updateCharacterImage(points: Int) {
        val imageResId = when (points) {
            in 0..100 -> R.drawable.ecoworld_basic
            in 101..500 -> R.drawable.ecoworld_mid
            in 501..1000 -> R.drawable.ecoworld_end
            else -> R.drawable.ecoworld_main
        }
        binding.ivCharacter.setImageResource(imageResId)
    }
}
