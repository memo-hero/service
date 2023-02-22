package com.memohero.infrastructure.repository

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import java.util.*

class InMemoryCardRepository: CardRepository {
    private val storedCards = mutableListOf<Card>()

    override suspend fun add(card: Card) {
        storedCards.add(card)
    }

    override suspend fun getByUserId(id: String) =
        storedCards.filter { card -> card.userId == id }

    override suspend fun getById(id: UUID) =
        storedCards.firstOrNull { it.id == id }

    override suspend fun getByTags(userId: String, tags: Set<String>): List<Card> {
        TODO("Not yet implemented")
    }

    override suspend fun update(card: Card) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCard(userId: String, cardId: String) {
        TODO("Not yet implemented")
    }
}