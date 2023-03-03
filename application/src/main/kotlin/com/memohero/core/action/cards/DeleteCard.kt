package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.UserRepository
import java.util.*

class DeleteCard(
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(userId: String, cardId: String) {
        if (userRepository.checkUserExists(userId).not())
            throw UserNotFoundException(userId)

        val card = cardRepository.getById(UUID.fromString(cardId))
            ?: throw CardNotFoundException(cardId = UUID.fromString(cardId))

        cardRepository.deleteCard(userId, card.id.toString())
    }
}