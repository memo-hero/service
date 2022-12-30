package com.memohero.core.domain.card

data class Card (
    val userId: String,
    val front: String,
    val back: String,
    val repetition: Int = 0 // The count of consecutive reviews with quality larger than 2
)