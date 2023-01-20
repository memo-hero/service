package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardAlreadyExistsException
import com.memohero.tools.mothers.getRandomNewCard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StoreCardTest {
    private val mockedRepository: CardRepository = mock()
    private val newCard = getRandomNewCard()

    @Test
    fun `should store new cards`() {
        val storeCard = StoreCard(mockedRepository)

        storeCard(newCard)

        verify(mockedRepository, times(1)).getById(newCard.id)
        verify(mockedRepository, times(1)).add(newCard)
    }

    @Test
    fun `should not store duplicated id cards`() {
        val storeCard = StoreCard(mockedRepository)
        whenever(mockedRepository.getById(newCard.id)).thenReturn(newCard)

        val ex = assertThrows<CardAlreadyExistsException> {
            storeCard(newCard)
        }
        verify(mockedRepository, times(1)).getById(newCard.id)
        verify(mockedRepository, times(0)).add(newCard)
        assertThat(ex.message).isEqualTo("Card with id ${ newCard.id } already exists.")
    }
}
