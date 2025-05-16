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

        // ✅ 상단 정보 표시 (더미 데이터)
        binding.tvTotalPoints.text = "1320 pt"
        binding.tvCO2Reduction.text = "CO₂ 절감: 152.0 kg"
        binding.tvTreeEffect.text = "나무 효과: 23그루"

        // ✅ 타임라인 더미 데이터
        val dummyData = listOf(
            TimelineEntry("2025-05-20", "포인트: +100pt, CO₂ 절감: +10kg (플라스틱 병 5개 분리배출)"),
            TimelineEntry("2025-05-19", "포인트: +50pt, CO₂ 절감: +5kg (종이 박스 3개 분리배출)"),
            TimelineEntry("2025-05-18", "포인트: +200pt, CO₂ 절감: +20kg (일일 미션 클리어)"),
            TimelineEntry("2025-05-17", "포인트: +30pt, CO₂ 절감: +3kg (음료 캔 2개 분리배출)"),
            TimelineEntry("2025-05-16", "포인트: +150pt, CO₂ 절감: +15kg (분리배출 챌린지 달성)"),
            TimelineEntry("2025-05-15", "포인트: +80pt, CO₂ 절감: +8kg (유리병 4개 분리배출)")
        )

        // ✅ RecyclerView 설정
        val adapter = TimelineAdapter(dummyData)
        binding.timelineRecyclerView.adapter = adapter
        binding.timelineRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
