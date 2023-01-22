package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemResponse
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toUser
import kotlinx.coroutines.runBlocking

class DynamoUserRepository(
    private val dynamoService: DynamoDbService
): UserRepository {
    private val dbTableName = "Users"

    override fun storeUser(user: User) {
        runBlocking {
            dynamoService.dynamoPutItemRequest(dbTableName, user.toDynamoMap())
        }
    }

    override fun getById(id: String): User? {
        val result: GetItemResponse
        runBlocking {
            result = dynamoService.dynamoGetItemRequest(dbTableName, "id", AttributeValue.S(id))
        }

        return result.toUser()
    }

    override fun checkUserExists(user: User): Boolean {
        return getById(user.id) != null
    }

    override fun updateUser(user: User) {
        runBlocking {
            dynamoService.dynamoPutItemRequest(dbTableName, user.toDynamoMap())
        }
    }
}

