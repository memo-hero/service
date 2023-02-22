package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class GetCardsByTag(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(userId: String, tags: Set<String>): List<Card> = cardRepository.getByTags(userId, tags)
}