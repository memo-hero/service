package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class GetCardsByTag(
    private val cardRepository: CardRepository
) {
    operator fun invoke(userId: String, tags: Set<String>): List<Card> {
        return cardRepository.getByTags(userId, tags)
    }
}