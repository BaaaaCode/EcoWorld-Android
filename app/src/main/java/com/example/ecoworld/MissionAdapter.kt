package com.example.ecoworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MissionAdapter(
    private val missions: List<Mission>,
    private val onRewardClick: (Mission) -> Unit
) : RecyclerView.Adapter<MissionAdapter.MissionViewHolder>() {

    inner class MissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.missionDescription)
        val progress: TextView = view.findViewById(R.id.missionProgress)
        val rewardButton: Button = view.findViewById(R.id.rewardButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mission, parent, false)
        return MissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        val mission = missions[position]
        holder.description.text = mission.description
        holder.progress.text = "진행: ${mission.currentCount} / ${mission.targetCount}"

        holder.rewardButton.isEnabled = mission.isCompleted
        holder.rewardButton.setOnClickListener {
            onRewardClick(mission)
        }
    }

    override fun getItemCount() = missions.size
}
