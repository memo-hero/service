package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.UserRepository
import java.util.*

class GetCardById(
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(userId: String, cardId: String): Card {
        if (userRepository.checkUserExists(userId).not())
            throw UserNotFoundException(userId)

        val userCards = cardRepository.getByUserId(userId)
        return userCards.firstOrNull { it.id == UUID.fromString(cardId) } ?: throw CardNotFoundException(UUID.fromString(cardId))
    }
}