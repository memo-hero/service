package com.memohero.infrastructure.repository.configuration

import com.memohero.core.domain.configuration.FeatureName
import com.memohero.infrastructure.Services
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlagsmithConfigurationRepositoryTest {

    @Test
    fun test() = runBlocking {
        val repo = Services.configRepository
        repo.initialize()
        val value = repo.getFeatureValue(FeatureName.LOKI_SERVICE_ENDPOINT)
        print(value)
    }
}