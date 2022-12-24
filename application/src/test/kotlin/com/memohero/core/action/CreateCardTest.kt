package com.memohero.core.action

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CreateCardTest {
    private val mockedRepository: CardRepository = mock()

    @Test
    fun `should store new cards`() {
        val storeCard = StoreCard(mockedRepository)
        val newCard = Card()

        storeCard(newCard)

        verify(mockedRepository, times(1)).add(newCard)
    }
}
