package com.memohero.infrastructure.logger.loki

import kotlinx.serialization.Serializable

@Serializable
data class StreamElement (
    val stream: Stream,
    val values: List<List<String>>
)