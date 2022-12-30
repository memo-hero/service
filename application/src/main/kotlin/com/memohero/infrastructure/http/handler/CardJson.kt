package com.memohero.infrastructure.http.handler

import com.memohero.core.domain.card.Card

data class CardJson(
    val userId: String,
    val front: String,
    val back: String,
    val repetition: Int = 0,
) {
    fun toCard(): Card {
        return Card(
            userId = userId,
            front = front,
            back = back,
        )
    }
}