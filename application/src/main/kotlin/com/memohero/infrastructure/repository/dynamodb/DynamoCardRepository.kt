package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toCard
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toCardList
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import kotlinx.coroutines.runBlocking
import java.util.*

class DynamoCardRepository(
    private val client: DynamoDbClient
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
        val partitionKeyName = "user_id"
        val attrNameAlias = mutableMapOf<String, String>()
        attrNameAlias["#a"] = "user_id"
        val attrValues = mutableMapOf<String, AttributeValue>()
        attrValues[":$partitionKeyName"] = AttributeValue.S(id)

        val request = QueryRequest {
            tableName = dbTableName
            keyConditionExpression = "#a = :$partitionKeyName"
            expressionAttributeNames = attrNameAlias
            this.expressionAttributeValues = attrValues
        }

        val result: QueryResponse
        runBlocking {
            result = client.query(request)
        }

        return result.toCardList()
    }

    override fun getById(id: UUID): Card? {
        val parameters = mutableListOf <AttributeValue>()
        parameters.add(AttributeValue.S(id.toString()))
        val result: ExecuteStatementResponse
        runBlocking {
            result = executeStatementPartiQL(client, "SELECT * FROM Cards WHERE card_id=?", parameters)
        }

        return result.toCard()
    }

    private suspend fun executeStatementPartiQL(
        ddb: DynamoDbClient,
        statementVal: String,
        parametersVal: List<AttributeValue>
    ): ExecuteStatementResponse {

        val request = ExecuteStatementRequest {
            statement = statementVal
            parameters = parametersVal
        }

        return ddb.executeStatement(request)
    }

    override fun getByTags(userId: String, tags: Set<String>): List<Card> {
        val parameters = mutableListOf <AttributeValue>()
        parameters.add(AttributeValue.S(userId))
        val result: ExecuteStatementResponse
        runBlocking {
            result = executeStatementPartiQL(client, "SELECT * FROM Cards WHERE user_id=?", parameters)
        }

        val cards = result.toCardList().filter { card ->
            card.tags.any { it in tags }
        }

        return cards
    }

    override fun getDueCards(): List<Card> {
        TODO("Not yet implemented")
    }

    override fun update(card: Card) {
        TODO("Not yet implemented")
    }
}