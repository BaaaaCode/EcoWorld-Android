package com.example.ecoworld

object MissionManager {

    val missions = mutableListOf(
        Mission(1, "오늘 5번 대화하기", 5, 0, false, 50),
        Mission(2, "포인트 100점 모으기", 100, 0, false, 100),
        Mission(3, "사진 업로드하기", 1, 0, false, 50)
    )

    // ✅ 미션 상태 업데이트
    fun updateMissionProgress(actionType: String) {
        missions.forEach { mission ->
            if (!mission.isCompleted) {
                when (actionType) {
                    "message" -> if (mission.description.contains("대화")) mission.currentCount++
                    "point" -> if (mission.description.contains("포인트")) mission.currentCount++
                    "photo_upload" -> if (mission.description.contains("사진")) mission.currentCount++
                }

                if (mission.currentCount >= mission.targetCount) {
                    mission.isCompleted = true
                }
            }
        }
    }

    // ✅ 일일 미션 모두 완료 여부
    fun isDailyMissionCompleted(): Boolean {
        return missions.all { it.isCompleted }
    }

    // ✅ 일일 미션 보상 지급 및 초기화 함수 추가
    fun claimDailyMissionReward(onReward: (Int) -> Unit) {
        if (isDailyMissionCompleted()) {
            onReward(300)  // 일일 미션 클리어 보상 포인트
            // 미션 초기화
            missions.forEach {
                it.isCompleted = false
                it.currentCount = 0
            }
        }
    }

    // ✅ 미션 보상 지급 및 초기화
    fun claimRewards(onReward: (Int) -> Unit) {
        missions.filter { it.isCompleted }.forEach { mission ->
            onReward(mission.rewardPoints)
            mission.isCompleted = false  // 반복 미션용
            mission.currentCount = 0
        }
    }
}
