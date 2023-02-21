package com.memohero.infrastructure.logger.loki

import com.memohero.core.domain.logging.LogSeverity

data class LokiConfiguration(
    val endpoint: String,
    private val minimumSeverity: String,
){
    fun getMinimumSeverity(): LogSeverity {
        return LogSeverity.values()[minimumSeverity.toInt()]
    }
}