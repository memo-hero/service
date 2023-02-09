package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.gamification.GamificationResult
import com.memohero.core.domain.gamification.IGamificationService
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.user.UserRepository
import java.util.*

class StudyCard(
    private val cardRepository: CardRepository,
    private val userRepository: UserRepository,
    private val spacedRepetitionService: ISpacedRepetitionService,
    private val gamificationService: IGamificationService
) {
    operator fun invoke(cardAnswer: CardAnswer): GamificationResult {
        val card = retrieveCard(cardAnswer.cardId)
            ?: throw CardNotFoundException(cardAnswer.cardId)

        val result = spacedRepetitionService.calculateInterval(card.studyMetadata, cardAnswer.quality)
        val updatedCard = card.updateMetadata(result)

        val user = retrieveUser(cardAnswer.userId) ?: throw UserNotFoundException(cardAnswer.userId)

        cardRepository.update(updatedCard)

        return if(cardAnswer.quality >= 3) gamificationService.grantExp(user, card.category)
        else gamificationService.applyDamage(user)
    }

    private fun retrieveCard(userId: UUID) = cardRepository.getById(userId)

    private fun retrieveUser(userId: String) = userRepository.getById(userId)
}