package com.example.ecoworld

import android.os.Bundle
import android.widget.Toast
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ecoworld.databinding.ActivityMissionBinding
import kotlin.random.Random

class MissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMissionBinding
    private var currentPoints = 1320  // 임시 포인트 (추후 DB 연동 예정)

    // ✅ 랜덤 미션 목록
    private val randomMissionList = listOf(
        "유리병 4개를 깨끗하게 씻어 분리배출 해주세요! (+80pt)",
        "음료 캔 2개를 깨끗하게 씻어 분리배출 해주세요! (+50pt)",
        "일회용 컵 대신 개인 텀블러를 사용해보세요! (+100pt)",
        "플라스틱과 종이를 정확히 분리배출 해보세요! (+120pt)"
    )

    // ✅ 기본 미션 2개 (요구사항 반영)
    private val basicMissions = listOf(
        "챗봇과 대화하기 (+30pt)",
        "카메라 사용하기 (+50pt)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 미션 목록 표시
        showMissions()

        // ✅ 미션 완료 버튼 클릭 → 포인트 적립
        binding.btnCompleteMission.setOnClickListener {
            currentPoints += 100  // 임시: 미션 완료 시 +100pt (추후 개별 포인트 반영 가능)
            Toast.makeText(this, "미션 완료! +100pt 적립", Toast.LENGTH_SHORT).show()

            // 새로운 미션 표시
            showMissions()
        }
    }

    // ✅ 미션 목록 표시 (기본 2개 + 랜덤 1개)
    private fun showMissions() {
        val missionContainer = binding.missionContainer
        missionContainer.removeAllViews()

        val missions = listOf(
            Triple("챗봇과 대화하기", 30, "달성 현황: 10회 / 목표: 20회"),
            Triple("카메라 사용하기", 50, "달성 현황: 3회 / 목표: 10회"),
            Triple("유리병 4개 분리배출", 80, "달성 현황: 0회 / 목표: 1회") // 랜덤 미션 예시
        )

        for ((title, points, status) in missions) {
            val missionCard = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(android.R.color.white)
                setPadding(24, 24, 24, 24)
                elevation = 8f
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 24)
                layoutParams = params
            }

            val missionTitle = TextView(this).apply {
                text = title
                textSize = 18f
                setTextColor(Color.parseColor("#333333"))
            }

            val missionPoints = TextView(this).apply {
                text = "포인트: $points pt"
                textSize = 16f
                setTextColor(Color.parseColor("#4CAF50"))
                setPadding(0, 8, 0, 0)
            }

            val missionStatus = TextView(this).apply {
                text = status
                textSize = 14f
                setTextColor(Color.parseColor("#666666"))
                setPadding(0, 8, 0, 0)
            }

            missionCard.apply {
                addView(missionTitle)
                addView(missionPoints)
                addView(missionStatus)
            }

            missionContainer.addView(missionCard)
        }
    }

}
