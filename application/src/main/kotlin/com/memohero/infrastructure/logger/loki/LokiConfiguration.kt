package com.memohero.infrastructure.logger.loki

data class LokiConfiguration(
    val endpoint: String = System.getenv("loki_endpoint") ?: "localhost",
)