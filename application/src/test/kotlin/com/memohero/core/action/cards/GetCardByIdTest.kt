package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomNewCard
import com.memohero.tools.mothers.getRandomUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCardByIdTest {
    private val mockedUserRepository: UserRepository = mock()
    private val mockedCardRepository: CardRepository = mock()
    private val getCardById = GetCardById(mockedUserRepository, mockedCardRepository)
    private val card = getRandomNewCard()
    private val user = getRandomUser(id = card.userId)

    @BeforeEach
    internal suspend fun setUp() {
        whenever(mockedUserRepository.checkUserExists(user.id)).thenReturn(true)
        whenever(mockedCardRepository.getByUserId(user.id)).thenReturn(listOf(card))
    }

    @Test
    suspend fun `should return a card`() {
        val result = getCardById(user.id, card.id.toString())

        assertThat(result).isEqualTo(card)
    }
}