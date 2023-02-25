package com.memohero.infrastructure.logger.loki

import java.util.*

class CircuitBreaker(
    private val maximumTries: Int,
    private val minutesUntilRetry: Int = 5,
    private var lastTry: Date = Calendar.getInstance().time
) {
    private var counter = 0

    internal fun isCircuitOpen(): Boolean {
        if (counter < maximumTries) return false

        return shouldRetry().not()
    }

    private fun shouldRetry(): Boolean {
        val diffInEpoch = Calendar.getInstance().time.time - lastTry.time
        val minutes = diffInEpoch / 1000 / 60
        if(minutes >= minutesUntilRetry) {
            lastTry = Calendar.getInstance().time
            return true
        }
        return false
    }

    fun success() {
        counter = 0
    }

    fun failure() {
        counter += 1
    }
}