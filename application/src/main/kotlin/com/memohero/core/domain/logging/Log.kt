package com.memohero.core.domain.logging

data class Log(
    val timestamp: Long,
    val message: String,
    val source: LogSource,
    val severity: LogSeverity,
)