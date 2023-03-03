package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class StoreCard(
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository,
) {
    suspend operator fun invoke(userId: String, card: Card) {
        if (userRepository.checkUserExists(userId).not())
            throw UserNotFoundException(userId)
        cardRepository.add(card)
    }
}