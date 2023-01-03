package com.memohero.core.domain.spacedrepetition.supermemo2

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Supermemo2Test {
    @Test
    fun `Should calculate correctly when the answer is positive`() {
        val result = Supermemo2.calculate(
            interval = 6,
            repetition = 2,
            easeFactor = 1.3,
            quality = 5,
        )

        assertThat(result.interval).isEqualTo(8)
        assertThat(result.repetition).isEqualTo(3)
        assertThat(result.easeFactor).isEqualTo(1.4000000000000001)
    }

    @Test
    fun `Should calculate correctly when the answer is negative`() {
        val result = Supermemo2.calculate(
            interval = 0,
            repetition = 0,
            easeFactor = 2.5,
            quality = 0,
        )

        assertThat(result.interval).isEqualTo(1)
        assertThat(result.repetition).isEqualTo(0)
        assertThat(result.easeFactor).isEqualTo(2.5)
    }

    @Test
    fun `Should always return the minimum ease factor`() {
        val result = Supermemo2.calculate(
            interval = 0,
            repetition = 0,
            easeFactor = 0.0,
            quality = 0,
        )

        assertThat(result.interval).isEqualTo(1)
        assertThat(result.repetition).isEqualTo(0)
        assertThat(result.easeFactor).isEqualTo(1.3)
    }
}