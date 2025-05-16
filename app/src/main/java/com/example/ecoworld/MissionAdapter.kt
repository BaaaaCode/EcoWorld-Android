package com.example.ecoworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoworld.databinding.ItemMissionBinding

class MissionAdapter(
    private val missions: List<Mission>,
    private val onRewardClick: (Mission) -> Unit
) : RecyclerView.Adapter<MissionAdapter.MissionViewHolder>() {

    inner class MissionViewHolder(val binding: ItemMissionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        val mission = missions[position]

        holder.binding.apply {
            tvMissionTitle.text = mission.description
            tvMissionProgress.text = "진행: ${mission.currentCount} / ${mission.targetCount}"

            btnReceiveReward.isEnabled = mission.isCompleted
            btnReceiveReward.setOnClickListener {
                onRewardClick(mission)
            }
        }
    }

    override fun getItemCount() = missions.size
}
