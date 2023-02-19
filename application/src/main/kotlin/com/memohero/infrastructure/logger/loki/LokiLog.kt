package com.memohero.infrastructure.logger.loki

import com.memohero.core.domain.logging.Log
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class LokiLog(
    private val streams: List<StreamElement>,
) {
    override fun toString(): String = Json.encodeToString(this)

    companion object {
        fun fromLog(log: Log): LokiLog = LokiLog(
            listOf(
                StreamElement(
                    stream = Stream(log.source.toString()),
                    listOf(listOf(
                        log.timestamp.toString(),
                        "${ log.severity } ${ log.message }"
                    ))
                )
            )
        )
    }
}