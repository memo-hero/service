package com.memohero.infrastructure

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.memohero.core.domain.configuration.ConfigurationRepository
import com.memohero.core.domain.configuration.FeatureName
import com.memohero.core.domain.gamification.GamificationService
import com.memohero.core.domain.gamification.IGamificationService
import com.memohero.core.domain.gamification.LevelAlgorithm
import com.memohero.core.domain.logging.ILogger
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.spacedrepetition.SpacedRepetitionService
import com.memohero.infrastructure.http.IHttpClient
import com.memohero.infrastructure.http.client.KtorHttpClient
import com.memohero.infrastructure.logger.loki.LokiClient
import com.memohero.infrastructure.logger.loki.LokiConfiguration
import com.memohero.infrastructure.logger.loki.LokiLoggerService
import com.memohero.infrastructure.repository.configuration.FlagsmithConfiguration
import com.memohero.infrastructure.repository.configuration.FlagsmithConfigurationRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoDbService
import kotlinx.coroutines.runBlocking

object Services {
    private val httpClient: IHttpClient by lazy { KtorHttpClient() }

    val configRepository: ConfigurationRepository by lazy {
        val config = FlagsmithConfiguration(envKey = System.getenv("flagsmith_env_key") ?: "default_value")
        FlagsmithConfigurationRepository(httpClient, config)
    }

    val dynamoDbService: DynamoDbService by lazy {
        var client: DynamoDbClient
        runBlocking { client = DynamoDbClient.fromEnvironment() }
        DynamoDbService(client)
    }

    val spacedRepetitionService: ISpacedRepetitionService by lazy {
        SpacedRepetitionService()
    }

    val gamificationService: IGamificationService by lazy {
        val levelAlgorithm = LevelAlgorithm()
        GamificationService(levelAlgorithm, Repositories.userRepository)
    }

    val loggerService: ILogger by lazy {
        val endpoint = configRepository.getFeatureValue(FeatureName.LOKI_SERVICE_ENDPOINT)
        val minimumSeverity = configRepository.getFeatureValue(FeatureName.SERVICE_MINIMUM_LOG_SEVERITY)
        val configuration = LokiConfiguration(endpoint, minimumSeverity)
        val client = LokiClient(configuration, httpClient)
        LokiLoggerService(configuration, client)
    }
}