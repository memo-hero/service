package com.memohero.core.domain.card

import java.util.*

data class Card (
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val front: String,
    val back: String,
    val studyMetadata: CardStudyMetadata = CardStudyMetadata(),
) {
    fun updateInterval(interval: Int): Card {
        val updatedMetadata = studyMetadata.copy(interval = interval)
        return this.copy(studyMetadata = updatedMetadata)
    }
}