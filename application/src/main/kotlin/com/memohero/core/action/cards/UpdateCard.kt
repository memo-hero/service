package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class UpdateCard (
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(card: Card) = cardRepository.update(card)
}