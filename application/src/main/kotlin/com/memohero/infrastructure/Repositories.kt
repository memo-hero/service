package com.memohero.infrastructure

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.InMemoryCardRepository
import com.memohero.infrastructure.repository.InMemoryUserRepository

object Repositories {
    val cardRepository: CardRepository by lazy {
        InMemoryCardRepository()
    }

    val userRepository: UserRepository by lazy {
        InMemoryUserRepository()
    }
}