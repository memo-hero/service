package com.memohero.infrastructure.repository.configuration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Flag (
    val feature: Feature,
    @SerialName("feature_state_value")
    val featureStateValue: JsonElement,
    val enabled: Boolean
)