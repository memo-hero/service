package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.CardMother
import com.memohero.tools.Supermemo2ResultMother
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class StudyCardTest {
    private val mockedCardRepository: CardRepository = mock()
    private val mockedUserRepository: UserRepository = mock()
    private val spacedRepetitionService: ISpacedRepetitionService = mock()
    private val quality = 5
    private val supermemoResult = Supermemo2ResultMother.getSupermemo2Result()
    private val card = CardMother.getNewCard()

    @Before
    fun setUp() {
        whenever(mockedCardRepository.getById(card.id)).thenReturn(card)
        whenever(spacedRepetitionService.calculateInterval(card.studyMetadata, quality)).thenReturn(supermemoResult)
    }

    @Test
    fun `studying a card should update a card's interval`() {
        val userId = "1"
        val answer = CardAnswer(userId = userId, cardId = card.id, quality)
        val updatedCard = card.updateMetadata(supermemoResult)
        val studyCard = StudyCard(
            mockedCardRepository,
            mockedUserRepository,
            spacedRepetitionService,
        )

        studyCard(answer)

        verify(mockedCardRepository, times(1)).getById(card.id)
        verify(mockedUserRepository, times(1)).getById(userId)
        verify(spacedRepetitionService, times(1)).calculateInterval(cardMetadata = card.studyMetadata, answer.quality)
        verify(mockedCardRepository, times(1)).update(updatedCard)
    }
}