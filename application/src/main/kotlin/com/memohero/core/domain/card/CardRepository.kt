package com.memohero.core.domain.card

import java.util.*

interface CardRepository {
    suspend fun add(card: Card)
    suspend fun getByUserId(id: String): List<Card>
    suspend fun getById(id: UUID): Card?
    suspend fun getByTags(userId: String, tags: Set<String>): List<Card>
    suspend fun update(card: Card)
    suspend fun deleteCard(userId: String, cardId: String)
}