package com.memohero.tools

import com.memohero.core.domain.card.CardStudyMetadata

object CardStudyMetadataMother {
    fun cardStudyMetadata(
        repetition: Int = getRandomInt(),
        easeFactor: Double = getRandomInt().toDouble(),
        interval: Long = getRandomInt().toLong(),
    ) = CardStudyMetadata(
        repetition = repetition,
        easeFactor = easeFactor,
        interval = interval,
    )
}