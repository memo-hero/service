package com.memohero.core.domain.card

data class CardStudyMetadata(
    val repetition: Int = 0,        // The count of consecutive reviews with quality larger than 2
    val easeFactor: Double = 2.5,   // A multiplier that affects the size of the interval, determine by the quality of the recall
    val interval: Long = 0,         // The gap/space between the next review
)