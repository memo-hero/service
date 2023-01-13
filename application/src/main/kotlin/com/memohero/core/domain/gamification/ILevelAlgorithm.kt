package com.memohero.core.domain.gamification

interface ILevelAlgorithm {
    fun check(currentLevel: Int, currentExp: Int): LevelCheck
}