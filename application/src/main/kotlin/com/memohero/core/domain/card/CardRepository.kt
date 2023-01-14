package com.memohero.core.domain.card

import java.util.*

interface CardRepository {
    fun add(card: Card)
    fun getByUserId(id: String): List<Card>
    fun getById(id: UUID): Card?
    fun getByTags(userId: String, tags: Set<String>): List<Card>
    fun update(card: Card)
}