package com.memohero.core.domain.card

import java.util.*

data class CardAnswer(
    val userId: String,
    val cardId: UUID,
    val quality: Int, // The quality of recalling the answer from a scale of 0 to 5
)