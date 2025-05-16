package com.example.ecoworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoworld.databinding.ActivityPointTimelineBinding

class PointTimelineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPointTimelineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 상단 정보 예시 (실제 데이터로 업데이트 가능)
        binding.tvTotalPoints.text = "1320 pt"
        binding.tvCO2Reduction.text = "CO₂ 절감: 152.0 kg"
        binding.tvTreeEffect.text = "나무 효과: 23그루"
        binding.ivCharacter.setImageResource(R.drawable.ecoworld_main) // 캐릭터 이미지 변경 가능

        // ✅ 타임라인 더미 데이터 (테스트용)
        val dummyData = listOf(
            TimelineEntry("2025-05-16", "포인트: +50pt, CO₂ 절감: +5kg"),
            TimelineEntry("2025-05-15", "포인트: +120pt, CO₂ 절감: +12kg"),
            TimelineEntry("2025-05-14", "포인트: +30pt, CO₂ 절감: +3kg")
        )

        // ✅ RecyclerView 어댑터 연결
        val adapter = TimelineAdapter(dummyData)
        binding.timelineRecyclerView.adapter = adapter
        binding.timelineRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
