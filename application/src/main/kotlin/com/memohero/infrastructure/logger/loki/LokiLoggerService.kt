package com.memohero.infrastructure.logger.loki

import com.memohero.core.domain.logging.ILogger
import com.memohero.core.domain.logging.Log
import com.memohero.core.domain.logging.LogSeverity
import com.memohero.core.domain.logging.LogSource
import java.time.Instant

class LokiLoggerService(
    private val client: LokiClient,
) : ILogger {

    override suspend fun log(log: Log) {
        client.push(LokiLog.fromLog(log))
    }

    override suspend fun log(message: String, severity: LogSeverity) {
        val timestamp = Instant.now().epochSecond.toString() + Instant.now().nano.toString()
        val log = Log(
            timestamp = timestamp.toLong(),
            message = message,
            source = LogSource.MEMOHERO_SERVICE,
            severity = severity,
        )
        log(log)
    }
}

