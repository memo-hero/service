package com.memohero.tools

import com.memohero.core.domain.spacedrepetition.supermemo2.Supermemo2Result

object Supermemo2ResultMother {
    fun getSupermemo2Result(
        interval: Long = getRandomInt().toLong(),
        repetition: Int = getRandomInt(),
        easeFactor: Double = getRandomInt().toDouble(),
    ) = Supermemo2Result(
            interval = interval,
            repetition = repetition,
            easeFactor = easeFactor,
        )
}