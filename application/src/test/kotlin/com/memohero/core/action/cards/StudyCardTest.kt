package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.user.UserRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class StudyCardTest {
    private val mockedCardRepository: CardRepository = mock()
    private val mockedUserRepository: UserRepository = mock()
    private val spacedRepetitionService: ISpacedRepetitionService = mock()
    private val quality = 5
    private val card = Card(
        id = UUID.randomUUID(),
        userId = "UserID",
        front = "front",
        back = "back",
    )

    @Before
    fun setUp() {
        whenever(mockedCardRepository.getById(card.id)).thenReturn(card)
        whenever(spacedRepetitionService.calculateInterval(card, quality)).thenReturn(1)
    }

    @Test
    fun `studying a card should update a card's interval`() {
        val userId = "1"
        val answer = CardAnswer(userId = userId, cardId = card.id, quality)
        val updatedCard = card.copy(studyMetadata = card.studyMetadata.copy(interval = 1))
        val studyCard = StudyCard(
            mockedCardRepository,
            mockedUserRepository,
            spacedRepetitionService,
        )

        studyCard(answer)

        verify(mockedCardRepository, times(1)).getById(card.id)
        verify(mockedUserRepository, times(1)).getById(userId)
        verify(spacedRepetitionService, times(1)).calculateInterval(card = card, answer.quality)
        verify(mockedCardRepository, times(1)).update(updatedCard)
    }
}