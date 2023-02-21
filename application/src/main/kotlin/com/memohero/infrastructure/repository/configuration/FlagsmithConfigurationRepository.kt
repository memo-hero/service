package com.memohero.infrastructure.repository.configuration

import com.memohero.core.domain.configuration.ConfigurationRepository
import com.memohero.core.domain.configuration.FeatureName
import com.memohero.core.domain.exceptions.FlagNotFoundException
import com.memohero.infrastructure.http.IHttpClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

class FlagsmithConfigurationRepository(
    private val client: IHttpClient,
    private val configuration: FlagsmithConfiguration
): ConfigurationRepository {
    private lateinit var flags: List<Flag>

    override suspend fun initialize() {
        val result = client.makeGet(configuration.baseUrl, configuration.header)
        flags = Json.decodeFromString(result)
    }

    override fun getFeatureValue(feature: FeatureName): String =
        getFlag(feature).featureStateValue.jsonPrimitive.content

    override fun isFeatureEnabled(feature: FeatureName): Boolean =
        getFlag(feature).enabled

    private fun getFlag(feature: FeatureName): Flag =
        flags.find { it.feature.name == feature.name.lowercase(Locale.getDefault()) }
            ?: throw FlagNotFoundException("Flag with name ${feature.name} was not found.")
}