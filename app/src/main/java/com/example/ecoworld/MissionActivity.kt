package com.example.ecoworld

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoworld.databinding.ActivityMissionBinding

class MissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMissionBinding
    private lateinit var missionAdapter: MissionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 뒤로 가기 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        missionAdapter = MissionAdapter(MissionManager.missions) { mission ->
            if (mission.isCompleted) {
                MissionManager.claimRewards { rewardPoints ->
                    Toast.makeText(this, "🎁 보상 +$rewardPoints 포인트 지급!", Toast.LENGTH_SHORT).show()
                }
                missionAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "아직 미션을 완료하지 않았어요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.missionRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MissionActivity)
            adapter = missionAdapter
        }
    }

    // ✅ 뒤로 가기 버튼 클릭 시 Activity 종료
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
