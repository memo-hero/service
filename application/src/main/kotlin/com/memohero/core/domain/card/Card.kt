package com.memohero.core.domain.card

data class Card (
    val userId: String,
    val front: String,
    val back: String,
    val studyMetadata: CardStudyMetadata,
)