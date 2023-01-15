package com.memohero.core.domain.card

import com.memohero.core.domain.user.Category
import java.time.LocalDate
import java.util.*

data class Card (
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val front: String,
    val back: String,
    val category: Category,
    val tags: MutableSet<String>,
    val dueDate: Long = LocalDate.now().toEpochDay(),
    val studyMetadata: CardStudyMetadata = CardStudyMetadata(),
) {
    fun updateMetadata(cardStudyMetadata: CardStudyMetadata) = this.copy(
        studyMetadata = cardStudyMetadata,
        dueDate = LocalDate.now().plusDays(cardStudyMetadata.interval).toEpochDay()
    )
}