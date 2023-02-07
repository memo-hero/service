package com.memohero.infrastructure.http.handler

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.user.Category

data class NewCardJson(
    val front: String,
    val back: String,
    val category: String,
    val tags: MutableSet<String>,
    val repetition: Int = 0,
) {
    fun toCard(userId: String): Card {
        return Card(
            userId = userId,
            front = front,
            back = back,
            tags = tags,
            category = Category.valueOf(category),
        )
    }
}