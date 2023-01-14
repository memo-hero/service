package com.memohero.core.domain.card

import com.memohero.core.domain.user.Category
import java.util.*

data class Card (
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val front: String,
    val back: String,
    val category: Category,
    val tags: MutableSet<String>,
    val studyMetadata: CardStudyMetadata = CardStudyMetadata(),
) {
    fun updateMetadata(cardStudyMetadata: CardStudyMetadata) = this.copy(studyMetadata = cardStudyMetadata)
}