package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import java.time.LocalDate

class GetDueCards(
    private val cardRepository: CardRepository
) {
    operator fun invoke(userId: String, tags: Set<String>): List<Card> {
        val currentDate = LocalDate.now().toEpochDay() + 1
        val dueCards = cardRepository.getByUserId(userId).filter { card -> card.dueDate <= currentDate}

        if (!tags.any()) {
            return dueCards
        }

        return dueCards.filter { card -> card.tags.any { it in tags } }
    }
}