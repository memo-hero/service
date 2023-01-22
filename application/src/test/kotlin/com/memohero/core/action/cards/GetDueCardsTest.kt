package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.tools.mothers.getRandomNewCard
import com.memohero.tools.mothers.getRandomUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetDueCardsTest {
    private val mockedRepository: CardRepository = mock()
    private val getDueCards = GetDueCards(mockedRepository)
    private val user = getRandomUser()
    private val card = getRandomNewCard(tags = mutableSetOf("tagA"))
    private val cards = listOf(
        card,
        getRandomNewCard(dueDate = card.dueDate.plus(1)),
        getRandomNewCard(dueDate = card.dueDate.minus(1), tags = mutableSetOf("tagB")),
    )

    @BeforeEach
    internal fun setUp() {
        whenever(mockedRepository.getByUserId(user.id)).thenReturn(cards)
    }

    @Test
    fun `should return due cards for current day or previous`() {
        val cards = getDueCards(user.id, emptySet())

        assertThat(cards.count()).isEqualTo(2)
    }

    @Test
    fun `should return due cards for the given tags`() {
        val cards = getDueCards(user.id, setOf("tagB"))

        assertThat(cards.count()).isEqualTo(1)
    }
}