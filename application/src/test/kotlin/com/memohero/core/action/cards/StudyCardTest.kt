package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.card.CardStudyMetadata
import com.memohero.core.domain.gamification.IGamificationService
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomNewCard
import com.memohero.tools.mothers.getRandomUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class StudyCardTest {
    private val mockedCardRepository: CardRepository = mock()
    private val mockedUserRepository: UserRepository = mock()
    private val spacedRepetitionService: ISpacedRepetitionService = mock()
    private val gamificationService: IGamificationService = mock()
    private val quality = 5
    private val cardStudyMetadata = CardStudyMetadata(0,0.0,0)
    private val card = getRandomNewCard()

    @BeforeEach
    fun setUp() {
        whenever(mockedCardRepository.getById(card.id)).thenReturn(card)
        whenever(mockedUserRepository.getById(card.userId)).thenReturn(getRandomUser(id = card.userId))
        whenever(spacedRepetitionService.calculateInterval(card.studyMetadata, quality)).thenReturn(cardStudyMetadata)
    }

    @Test
    fun `studying a card should update a card's interval`() {
        val userId = card.userId
        val answer = CardAnswer(userId = userId, cardId = card.id, quality)
        val updatedCard = card.updateMetadata(cardStudyMetadata)
        val studyCard = StudyCard(
            mockedCardRepository,
            mockedUserRepository,
            spacedRepetitionService,
            gamificationService,
        )

        studyCard(answer)

        verify(mockedCardRepository, times(1)).getById(card.id)
        verify(mockedUserRepository, times(1)).getById(userId)
        verify(spacedRepetitionService, times(1)).calculateInterval(cardMetadata = card.studyMetadata, answer.quality)
        verify(mockedCardRepository, times(1)).update(updatedCard)
    }
}