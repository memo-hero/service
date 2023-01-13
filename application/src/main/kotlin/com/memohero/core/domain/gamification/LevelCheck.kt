package com.memohero.core.domain.gamification

data class LevelCheck(
    val didLevelUp: Boolean = false,
    val expDifference: Int = 0,
)