package com.memohero.infrastructure.repository.configuration

import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val id: Int,
    val type: String,
    val name: String,
)