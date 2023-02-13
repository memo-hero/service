package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository

class DeleteCard(
    private val cardRepository: CardRepository
) {
    operator fun invoke(userId: String, cardId: String) {
        cardRepository.deleteCard(userId, cardId)
    }
}