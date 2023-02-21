package com.memohero.core.domain.configuration

interface ConfigurationRepository {
    suspend fun initialize()
    fun getFeatureValue(feature: FeatureName): String
    fun isFeatureEnabled(feature: FeatureName): Boolean
}