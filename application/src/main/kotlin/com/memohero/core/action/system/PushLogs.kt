package com.memohero.core.action.system

import com.memohero.core.domain.logging.ILogger
import com.memohero.core.domain.logging.Log

class PushLogs(
    private val logger: ILogger,
) {
    suspend operator fun invoke(log: List<Log>) {
        logger.log(log)
    }
}