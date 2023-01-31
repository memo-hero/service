package com.memohero.core.domain.gamification

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

class LevelAlgorithm : ILevelAlgorithm {
    override fun check(currentLevel: Int, currentExp: Int): LevelCheck {
        val expNeeded = getExpNeededToLevelUp(currentLevel)

        return LevelCheck(
            didLevelUp = currentExp >= expNeeded,
            expDifference = abs(currentExp - expNeeded),
            expNeeded = expNeeded
        )
    }

    private fun getExpNeededToLevelUp(currentLevel: Int): Int {
        val nextLevel = currentLevel + 1
        return ((0.04 * (nextLevel.toDouble().pow(3)) + 0.8 * (nextLevel.toDouble().pow(2)) + 2 * nextLevel) * 10).roundToInt()
    }
}