package com.memohero.core.domain.card

import java.util.*

data class Card (
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val front: String,
    val back: String,
    val studyMetadata: CardStudyMetadata = CardStudyMetadata(),
)