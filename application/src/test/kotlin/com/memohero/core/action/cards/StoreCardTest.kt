package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomNewCard
import com.memohero.tools.mothers.getRandomUser
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class StoreCardTest {
    private val mockedUserRepository: UserRepository = mock()
    private val mockedCardRepository: CardRepository = mock()
    private val user = getRandomUser()
    private val newCard = getRandomNewCard()

    @Test
    suspend fun `should store new cards`() {
        val storeCard = StoreCard(mockedUserRepository, mockedCardRepository)

        storeCard(user.id, newCard)

        verify(mockedCardRepository, times(1)).add(newCard)
    }
}
