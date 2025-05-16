package com.example.ecoworld

data class Mission(
    val id: Int,
    val description: String,
    val targetCount: Int,
    var currentCount: Int = 0,
    var isCompleted: Boolean = false,
    val rewardPoints: Int
)
