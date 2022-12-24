package com.memohero.core.action

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class StoreCard(
    private val cardRepository: CardRepository
) {
    operator fun invoke(card: Card) {
        cardRepository.add(card)
    }
}