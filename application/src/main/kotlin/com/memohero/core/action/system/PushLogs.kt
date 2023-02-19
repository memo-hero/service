package com.memohero.core.action.system

import com.memohero.core.domain.logging.ILogger
import com.memohero.core.domain.logging.Log

class PushLogs(
    private val logger: ILogger,
) {
    suspend operator fun invoke(userId: String, logs: List<Log>) {
        val updatedLogs = logs.map { log ->
            Log(
                timestamp = log.timestamp,
                message = "user_id=${ userId } ${log.message}",
                source = log.source,
                severity = log.severity,
            )
        }
        logger.log(updatedLogs)
    }
}