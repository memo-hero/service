package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCardsTest {
    private val mockedRepository: CardRepository = mock()
    private val userId = "some id"
    private val card = Card(userId = userId, front = "front", back = "back")

    @Before
    fun setUp() {
        whenever(mockedRepository.getByUserId(userId)).thenReturn(listOf(card))
    }

    @Test
    fun `Should return all cards for a given user`() {
        val getCards = GetCards(mockedRepository)

        val result = getCards(userId)

        assertThat(result.size).isGreaterThan(0)
    }

    @Test
    fun `Should not return cards of a different user`() {
        val getCards = GetCards(mockedRepository)

        val result = getCards("some other id")

        assertThat(result.size).isEqualTo(0)
    }
}