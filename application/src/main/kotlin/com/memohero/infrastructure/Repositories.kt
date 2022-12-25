package com.memohero.infrastructure

import com.memohero.core.domain.card.CardRepository
import com.memohero.infrastructure.repository.InMemoryCardRepository

object Repositories {
    val cardRepository: CardRepository by lazy {
        InMemoryCardRepository()
    }
}