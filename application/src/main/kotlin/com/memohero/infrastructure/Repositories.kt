package com.memohero.infrastructure

import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoCardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoUserRepository

object Repositories {

    val cardRepository: CardRepository by lazy {
        DynamoCardRepository(Services.dynamoDbService)
    }

    val userRepository: UserRepository by lazy {
        DynamoUserRepository(Services.dynamoDbService)
    }
}