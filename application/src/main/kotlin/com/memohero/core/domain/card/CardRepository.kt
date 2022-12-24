package com.memohero.core.domain.card

interface CardRepository {
    fun add(card: Card)
    fun getByUserId(id: String): List<Card>
}