package com.memohero.core.domain.card

import com.memohero.core.domain.spacedrepetition.supermemo2.Supermemo2Result
import java.util.*

data class Card (
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val front: String,
    val back: String,
    val studyMetadata: CardStudyMetadata = CardStudyMetadata(),
) {
    fun updateMetadata(supermemo2Result: Supermemo2Result): Card {
        return this.copy(studyMetadata = studyMetadata.copy(
            interval = supermemo2Result.interval,
            easeFactor = supermemo2Result.easeFactor,
            repetition = supermemo2Result.repetition,
        ))
    }
}