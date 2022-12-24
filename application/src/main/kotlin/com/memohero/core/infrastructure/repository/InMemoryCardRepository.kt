package com.memohero.core.infrastructure.repository

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository

class InMemoryCardRepository: CardRepository {
    private val storedCards = mutableListOf<Card>()

    override fun add(card: Card) {
        storedCards.add(card)
    }

    override fun getByUserId(id: String): List<Card> {
        return storedCards.filter { card -> card.userId == id }
    }
}