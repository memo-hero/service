package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toUser
import kotlinx.coroutines.runBlocking

class DynamoUserRepository(
    private val client: DynamoDbClient
): UserRepository {
    private val dbTableName = "Users"

    override fun storeUser(user: User) {
        val request = PutItemRequest {
            tableName = dbTableName
            item = user.toDynamoMap()
        }

        runBlocking {
            client.putItem(request)
        }
    }

    override fun getById(id: String): User? {
        val result: GetItemResponse
        val request = GetItemRequest {
            key = mutableMapOf<String, AttributeValue>("id" to AttributeValue.S(id))
            tableName = dbTableName
        }
        runBlocking {
            result = client.getItem(request)
        }

        return result.toUser()
    }

    override fun checkUserExists(user: User): Boolean {
        val result: GetItemResponse
        val request = GetItemRequest {
            key = mutableMapOf<String, AttributeValue>("id" to AttributeValue.S(user.id))
            tableName = dbTableName
        }
        runBlocking {
            result = client.getItem(request)
        }

        return result.toUser() != null
    }

    override fun updateUser(user: User) {
        val request = PutItemRequest {
            tableName = dbTableName
            item = user.toDynamoMap()
        }

        runBlocking {
            client.putItem(request)
        }
    }
}

