package com.memohero.tools.mothers

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardStudyMetadata
import com.memohero.core.domain.user.Category
import com.memohero.tools.getRandomInt
import com.memohero.tools.getRandomString
import java.util.*

fun getRandomNewCard(
    id: UUID = UUID.randomUUID(),
    userId: String = getRandomString(10),
    front: String = getRandomString(10),
    back: String = getRandomString(10),
    category: Category = Category.values().random(),
    tags: MutableSet<String> = mutableSetOf(getRandomString(5)),
    studyMetadata: CardStudyMetadata = getRandomCardStudyMetadata(),
) = Card(
    id = id,
    userId = userId,
    front = front,
    back = back,
    category = category,
    tags = tags,
    studyMetadata = studyMetadata
)

fun getRandomCardStudyMetadata(
    repetition: Int = getRandomInt(),
    easeFactor: Double = getRandomInt().toDouble(),
    interval: Long = getRandomInt().toLong(),
) = CardStudyMetadata(
    repetition = repetition,
    easeFactor = easeFactor,
    interval = interval,
)