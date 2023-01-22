package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardNotFoundException
import java.util.*

class GetCardById(
    private val cardRepository: CardRepository
) {
    operator fun invoke(userId: String, cardId: String): Card {
        val userCards = cardRepository.getByUserId(userId)
        return userCards.firstOrNull { it.id == UUID.fromString(cardId) } ?: throw CardNotFoundException(UUID.fromString(cardId))
    }
}