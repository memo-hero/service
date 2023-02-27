package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.tools.mothers.getRandomNewCard
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class StoreCardTest {
    private val mockedRepository: CardRepository = mock()
    private val newCard = getRandomNewCard()

    @Test
    suspend fun `should store new cards`() {
        val storeCard = StoreCard(mockedRepository)

        storeCard(newCard)

        verify(mockedRepository, times(1)).add(newCard)
    }
}
