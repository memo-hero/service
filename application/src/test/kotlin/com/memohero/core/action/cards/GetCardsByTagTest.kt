package com.memohero.core.action.cards

import com.memohero.core.domain.card.CardRepository
import com.memohero.tools.mothers.getRandomNewCard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCardsByTagTest {
    private val mockedRepository: CardRepository = mock()
    private val card = getRandomNewCard()
    private val tag = "tag1"
    private val tags = setOf(tag)

    @BeforeEach
    suspend fun setUp() {
        whenever(mockedRepository.getByTags(card.userId, tags)).thenReturn(listOf(card))
    }

    @Test
    suspend fun `should return all cards for a given tag`() {
        val getByTags = GetCardsByTag(mockedRepository)

        val cards = getByTags(card.userId, tags)

        assertThat(cards[0].tags.contains(tag))
    }
}