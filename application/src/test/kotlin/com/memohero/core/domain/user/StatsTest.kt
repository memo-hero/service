package com.memohero.core.domain.user

import com.memohero.tools.CategoryStatsMother
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StatsTest {
    @Test
    fun `general level should return the average level of the categories`() {
        val artsCategory = CategoryStatsMother.getCategoryStat(type = Category.ARTS, level = 1)
        val computersCategory = CategoryStatsMother.getCategoryStat(type = Category.COMPUTERS, level = 2)
        val historyCategory = CategoryStatsMother.getCategoryStat(type = Category.HISTORY, level = 3)
        val languagesCategory = CategoryStatsMother.getCategoryStat(type = Category.LANGUAGES, level = 4)
        val scienceCategory = CategoryStatsMother.getCategoryStat(type = Category.SCIENCE, level = 5)

        val stats = Stats(listOf(
            artsCategory,
            computersCategory,
            historyCategory,
            languagesCategory,
            scienceCategory,
        ))

        assertThat(stats.generalLevel).isEqualTo(3)
    }
}