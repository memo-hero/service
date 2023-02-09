package com.memohero.core.domain.gamification

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.roundToInt

class LevelAlgorithm : ILevelAlgorithm {
    override fun check(currentLevel: Int, currentExp: Int): LevelCheck {
        val expNeeded = getExpNeededToLevelUp(currentLevel)
        val didLevelUp = currentExp >= expNeeded

        return LevelCheck(
            didLevelUp = didLevelUp,
            expDifference = abs(currentExp - expNeeded),
            expNeeded = if (didLevelUp) getExpNeededToLevelUp(currentLevel + 1) else expNeeded
        )
    }

    private fun getExpNeededToLevelUp(currentLevel: Int): Int {
        val nextLevel = currentLevel + 1
        return ((0.04 * (nextLevel.toDouble().pow(3)) + 0.8 * (nextLevel.toDouble().pow(2)) + 2 * nextLevel) * 10).roundToInt()
    }
}