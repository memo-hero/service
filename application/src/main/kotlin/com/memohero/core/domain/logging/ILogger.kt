package com.memohero.core.domain.logging

interface ILogger {
    suspend fun log(log: Log)
    suspend fun log(message: String, severity: LogSeverity = LogSeverity.INFO)
}