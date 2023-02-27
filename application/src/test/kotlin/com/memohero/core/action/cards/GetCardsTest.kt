package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.tools.mothers.getRandomNewCard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCardsTest {
    private val mockedRepository: CardRepository = mock()
    private val card = getRandomNewCard()

    @BeforeEach
    suspend fun setUp() {
        whenever(mockedRepository.getByUserId(card.userId)).thenReturn(listOf(card))
    }

    @Test
    suspend fun `Should return all cards for a given user`() {
        val getCards = GetCards(mockedRepository)

        val result = getCards(card.userId)

        assertThat(result.size).isGreaterThan(0)
    }

    @Test
    suspend fun `Should not return cards of a different user`() {
        val getCards = GetCards(mockedRepository)

        val result = getCards("some other id")

        assertThat(result.size).isEqualTo(0)
    }
}