package com.memohero.infrastructure.http.handler

import com.memohero.core.domain.card.Card

data class CardJson(
    val userId: String
) {
    fun toCard(): Card {
        return Card(
            userId = userId
        )
    }
}