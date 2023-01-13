package com.memohero.core.domain.spacedrepetition.supermemo2

import kotlin.math.max
import kotlin.math.roundToLong

object Supermemo2 {
    fun calculate(
        interval: Long,
        repetition: Int,
        easeFactor: Double,
        quality: Int,
    ): Supermemo2Result {
        val updatedInterval = calculateNextInterval(quality, repetition, interval, easeFactor)
        val updatedRepetition = updateRepetition(quality, repetition)
        val updatedEaseFactor = calculateEaseFactor(quality, easeFactor)

        return Supermemo2Result(
            interval = updatedInterval,
            repetition = updatedRepetition,
            easeFactor = updatedEaseFactor,
        )
    }

    private fun calculateNextInterval(quality: Int, repetition: Int, interval: Long, easeFactor: Double): Long {
        return if(quality < 3) 1
        else when(repetition) {
            0 -> 1
            1 -> 6
            else -> (interval * easeFactor).roundToLong()
        }
    }

    private fun updateRepetition(quality: Int, repetition: Int): Int =
        if(quality >= 3) repetition + 1 else 0

    private fun calculateEaseFactor(quality: Int, easeFactor: Double): Double {
        val minimumEaseFactor = 1.3
        val newEaseFactor = if(quality < 3) easeFactor
        else easeFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02))

        return max(newEaseFactor, minimumEaseFactor)
    }
}
