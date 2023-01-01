package com.memohero.infrastructure.repository

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import java.util.*

class InMemoryCardRepository: CardRepository {
    private val storedCards = mutableListOf<Card>()

    override fun add(card: Card) {
        storedCards.add(card)
    }

    override fun getByUserId(id: String) =
        storedCards.filter { card -> card.userId == id }

    override fun getById(id: UUID) =
        storedCards.firstOrNull { it.id == id }

    override fun update(card: Card) {
        TODO("Not yet implemented")
    }
}