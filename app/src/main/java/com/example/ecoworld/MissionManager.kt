package com.example.ecoworld

object MissionManager {

    // ✅ 테스트용 일일 미션 초기화
    val missions = mutableListOf(
        Mission(1, "오늘 5번 대화하기", 5, 0, false, 50),
        Mission(2, "포인트 100점 모으기", 100, 0, false, 100)
    )

    // ✅ 미션 상태 업데이트
    fun updateMissionProgress(actionType: String) {
        missions.forEach { mission ->
            if (!mission.isCompleted) {
                when (actionType) {
                    "message" -> mission.currentCount++
                    "point" -> mission.currentCount++  // 필요 시 다른 처리 가능
                }

                if (mission.currentCount >= mission.targetCount) {
                    mission.isCompleted = true
                }
            }
        }
    }

    // ✅ 완료된 미션 보상 지급
    fun claimRewards(onReward: (Int) -> Unit) {
        missions.filter { it.isCompleted }.forEach { mission ->
            onReward(mission.rewardPoints)
            mission.isCompleted = false  // ✅ 반복 미션용 (원하면 제거 가능)
            mission.currentCount = 0     // ✅ 미션 리셋
        }
    }
}
