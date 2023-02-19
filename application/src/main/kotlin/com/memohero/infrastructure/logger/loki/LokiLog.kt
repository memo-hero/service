package com.memohero.infrastructure.logger.loki

import com.memohero.core.domain.logging.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LokiLog(
    private val streams: List<StreamElement>,
) {
    override fun toString(): String = Json.encodeToString(this)

    companion object {
        fun fromLog(log: Log): LokiLog = LokiLog(
            log.toStreamElement()
        )

        fun fromLogs(log: List<Log>): LokiLog {
            val logs = log.map { it.toLokiLogLine() }
            return LokiLog(
                listOf(
                    StreamElement(
                        stream = Stream(log.first().source.toString()),
                        values = logs,
                    )
                )
            )
        }

        private fun Log.toStreamElement() = listOf(
            StreamElement(
                stream = Stream(this.source.toString()),
                values = listOf(this.toLokiLogLine())
            )
        )

        private fun Log.toLokiLogLine() = listOf(
            this.timestamp.toString(),
            "${ this.severity } ${ this.message }"
        )
    }
}