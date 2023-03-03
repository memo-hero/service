package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemResponse
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.logging.LogSeverity
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.Services
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toUser
import kotlinx.coroutines.runBlocking

class DynamoUserRepository(
    private val dynamoService: DynamoDbService
): UserRepository {
    private val dbTableName = "Users"

    override suspend fun storeUser(user: User) {
        Services.loggerService.log("new_user=$user")
        dynamoService.dynamoPutItemRequest(dbTableName, user.toDynamoMap())
    }

    override suspend fun getById(id: String): User? {
        Services.loggerService.log("retrieving_user_id=$id")
        val result = dynamoService.dynamoGetItemRequest(dbTableName, "id", AttributeValue.S(id))

        return result.toUser()
    }

    override suspend fun checkUserExists(user: User): Boolean {
        Services.loggerService.log("checking_if_user_exists=${user.id}")
        return getById(user.id) != null
    }

    override suspend fun checkUserExists(userId: String): Boolean {
        Services.loggerService.log("checking_if_user_exists=${userId}")
        return getById(userId) != null
    }

    override suspend fun updateUser(user: User) {
        Services.loggerService.log("updating_user=${user.id}")
        dynamoService.dynamoPutItemRequest(dbTableName, user.toDynamoMap())
    }

    override suspend fun makePutTransaction(user: User, card: Card) {
        Services.loggerService.log("updating_user=${user.id} updating_card=${card.id}")
        dynamoService.makePutTransaction("Users", user.toDynamoMap(), "Cards", card.toDynamoMap())
    }
}

