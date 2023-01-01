package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.user.UserRepository
import java.util.*

class StudyCard(
    private val cardRepository: CardRepository,
    private val userRepository: UserRepository,
    private val spacedRepetitionService: ISpacedRepetitionService,
) {
    operator fun invoke(cardAnswer: CardAnswer) {
        val card = retrieveCard(cardAnswer.cardId)
            ?: throw CardNotFoundException(cardAnswer.cardId)
        val user = retrieveUser(cardAnswer.userId)

        val newInterval = spacedRepetitionService.calculateInterval(card, cardAnswer.quality)
        val updatedCard = card.updateInterval(newInterval)

        cardRepository.update(updatedCard)
    }

    private fun retrieveCard(cardId: UUID) = cardRepository.getById(cardId)

    private fun retrieveUser(userId: String) = userRepository.getById(userId)
}