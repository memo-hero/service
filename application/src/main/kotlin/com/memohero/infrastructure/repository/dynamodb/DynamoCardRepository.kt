package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import kotlinx.coroutines.runBlocking
import java.util.*

class DynamoCardRepository(
    private val client: DynamoDbClient = DynamoDbClient { region = "sa-east-1" }
): CardRepository {
    private val dbTableName = "Cards"

    override fun add(card: Card) {
        val request = PutItemRequest {
            tableName = dbTableName
            item = card.toDynamoMap()
        }

        runBlocking {
            client.putItem(request)
        }
    }

    override fun getByUserId(id: String): List<Card> {
        TODO("Not yet implemented")
    }

    override fun getById(id: UUID): Card? {
        return null
    }

    override fun getByTags(userId: String, tags: Set<String>): List<Card> {
        TODO("Not yet implemented")
    }

    override fun update(card: Card) {
        TODO("Not yet implemented")
    }
}