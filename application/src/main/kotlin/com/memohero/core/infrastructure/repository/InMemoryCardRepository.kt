package com.memohero.core.infrastructure.repository

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class InMemoryCardRepository: CardRepository {
    override fun add(card: Card) {
        TODO("Not yet implemented")
    }

    override fun getByUserId(id: String): List<Card> {
        TODO("Not yet implemented")
    }
}