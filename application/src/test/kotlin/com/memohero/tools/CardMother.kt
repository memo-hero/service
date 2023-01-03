package com.memohero.tools

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardStudyMetadata
import java.util.*

object CardMother {
    fun getNewCard(
        id: UUID = UUID.randomUUID(),
        userId: String = getRandomString(10),
        front: String = getRandomString(10),
        back: String = getRandomString(10),
        studyMetadata: CardStudyMetadata = CardStudyMetadataMother.cardStudyMetadata(),
    ) = Card(
        id = id,
        userId = userId,
        front = front,
        back = back,
        studyMetadata = studyMetadata
    )
}