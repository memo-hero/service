package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class GetCards (
    private val cardRepository: CardRepository
) {
    operator fun invoke(userId: String): List<Card> {
        return cardRepository.getByUserId(userId)
    }
}