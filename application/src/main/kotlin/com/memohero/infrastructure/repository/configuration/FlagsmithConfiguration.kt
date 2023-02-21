package com.memohero.infrastructure.repository.configuration

data class FlagsmithConfiguration(
    private val envKey: String,
    val baseUrl: String = "https://edge.api.flagsmith.com/api/v1/flags/",
    val header: Pair<String, String> = "X-Environment-Key" to envKey,
)