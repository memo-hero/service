package com.memohero.infrastructure

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoCardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoUserRepository

object Repositories {
    private const val dynamoRegion = "sa-east-1"
    private val dynamoClient by lazy {
        DynamoDbClient { region = dynamoRegion }
    }

    val cardRepository: CardRepository by lazy {
        DynamoCardRepository(Services.dynamoDbService)
    }

    val userRepository: UserRepository by lazy {
        DynamoUserRepository(dynamoClient)
    }
}