package com.memohero.core.domain.user

import com.memohero.tools.mothers.getRandomCategoryStat
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StatsTest {
    @Test
    fun `general level should return the average level of the categories`() {
        val artsCategory = getRandomCategoryStat(category = Category.ARTS, level = 1)
        val computersCategory = getRandomCategoryStat(category = Category.COMPUTERS, level = 2)
        val historyCategory = getRandomCategoryStat(category = Category.HISTORY, level = 3)
        val languagesCategory = getRandomCategoryStat(category = Category.LANGUAGES, level = 4)
        val scienceCategory = getRandomCategoryStat(category = Category.SCIENCE, level = 5)

        val stats = Stats(
            categories = listOf(
                artsCategory,
                computersCategory,
                historyCategory,
                languagesCategory,
                scienceCategory,
        ))

        assertThat(stats.generalLevel).isEqualTo(3)
    }
}