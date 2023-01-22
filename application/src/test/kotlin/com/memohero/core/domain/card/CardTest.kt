package com.memohero.core.domain.card

import com.memohero.tools.mothers.getRandomNewCard
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CardTest {
    @Test
    fun `should start with a due date of the current date`() {
        val card = getRandomNewCard(studyMetadata = CardStudyMetadata())
        val currentDate = getToday()

        assertThat(card.dueDate).isEqualTo(currentDate)
    }

    @Test
    fun `should return the next day when the interval is 1`() {
        val card = getRandomNewCard(studyMetadata = CardStudyMetadata(interval = 0))
        val currentDate = getTomorrow()
        val updatedCard = card.updateMetadata(CardStudyMetadata(interval = 1))

        assertThat(updatedCard.dueDate).isEqualTo(currentDate)
    }

    private fun getToday() = LocalDate.now().toEpochDay()
    private fun getTomorrow() = LocalDate.now().plusDays(1).toEpochDay()
}