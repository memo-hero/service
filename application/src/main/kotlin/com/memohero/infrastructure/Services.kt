package com.memohero.infrastructure

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.memohero.core.domain.gamification.GamificationService
import com.memohero.core.domain.gamification.IGamificationService
import com.memohero.core.domain.gamification.LevelAlgorithm
import com.memohero.core.domain.logging.ILogger
import com.memohero.core.domain.spacedrepetition.ISpacedRepetitionService
import com.memohero.core.domain.spacedrepetition.SpacedRepetitionService
import com.memohero.infrastructure.logger.loki.LokiClient
import com.memohero.infrastructure.logger.loki.LokiConfiguration
import com.memohero.infrastructure.logger.loki.LokiLoggerService
import com.memohero.infrastructure.repository.dynamodb.DynamoDbService

object Services {
    val dynamoDbService: DynamoDbService by lazy {
        val dynamoRegion = "sa-east-1"
        val client = DynamoDbClient { region = dynamoRegion }
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
        val client = LokiClient(LokiConfiguration())
        LokiLoggerService(client)
    }
}