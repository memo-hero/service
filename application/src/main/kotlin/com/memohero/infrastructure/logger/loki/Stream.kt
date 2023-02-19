package com.memohero.infrastructure.logger.loki

import kotlinx.serialization.Serializable

@Serializable
data class Stream (
    val source: String
)