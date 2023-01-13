package com.memohero.core.domain.user

import com.memohero.tools.mothers.getRandomCategoryProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StatsTest {
    @Test
    fun `general level should return the average level of the categories`() {
        val stats = Stats(
            categories = mutableMapOf(
                Category.ARTS to getRandomCategoryProperties(level = 1),
                Category.COMPUTERS to getRandomCategoryProperties(level = 2),
                Category.HISTORY to getRandomCategoryProperties(level = 3),
                Category.LANGUAGES to getRandomCategoryProperties(level = 4),
                Category.SCIENCE to getRandomCategoryProperties(level = 5),
        ))

        assertThat(stats.generalLevel).isEqualTo(3)
    }
}