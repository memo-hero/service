package com.memohero.infrastructure

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.configuration.FeatureName
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.InMemoryCardRepository
import com.memohero.infrastructure.repository.InMemoryUserRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoCardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoUserRepository

object Repositories {

    val cardRepository: CardRepository by lazy {
        if (useInMemory()) InMemoryCardRepository()
        else DynamoCardRepository(Services.dynamoDbService)
    }

    val userRepository: UserRepository by lazy {
        if (useInMemory()) InMemoryUserRepository()
        else DynamoUserRepository(Services.dynamoDbService)
    }

    private fun useInMemory() =
        Services.configRepository.isFeatureEnabled(FeatureName.SERVICE_USE_IN_MEMORY_REPOSITORY)
}