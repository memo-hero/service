package com.memohero.core.domain.gamification

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LevelAlgorithmTest {
    @Test
    fun `Should return how much exp is needed for the next level`() {
        val algorithm = LevelAlgorithm()
        val level = 1
        val exp = 70

        val result = algorithm.check(level, exp)

        assertThat(result.didLevelUp).isEqualTo(false)
        assertThat(result.expDifference).isEqualTo(5)
    }

    @Test
    fun `Should return if the level up is granted and any exceeded exp`() {
        val algorithm = LevelAlgorithm()
        val level = 1
        val exp = 100

        val result = algorithm.check(level, exp)

        assertThat(result.didLevelUp).isEqualTo(true)
        assertThat(result.expDifference).isEqualTo(25)
    }
}