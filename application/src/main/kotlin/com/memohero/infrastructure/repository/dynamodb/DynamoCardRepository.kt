package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toCardList
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import kotlinx.coroutines.runBlocking
import java.util.*

class DynamoCardRepository(
    private val dynamoService: DynamoDbService
): CardRepository {
    private val dbTableName = "Cards"

    override fun add(card: Card) {
        runBlocking {
            dynamoService.dynamoPutItemRequest(dbTableName, card.toDynamoMap())
        }
    }

    override fun getByUserId(id: String): List<Card> {
        val partitionKeyName = "user_id"
        val attributeAlias = "#a"
        val attributeNames = mutableMapOf(attributeAlias to partitionKeyName)
        val attributeValues = mutableMapOf<String, AttributeValue>(":$partitionKeyName" to AttributeValue.S(id))
        val keyConditionExpression = "$attributeAlias = :$partitionKeyName"

        val result: QueryResponse
        runBlocking {
            result = dynamoService.dynamoQueryRequest(dbTableName, keyConditionExpression, attributeNames, attributeValues)
        }

        return result.toCardList()
    }

    override fun getById(id: UUID): Card? {
//        val parameters = mutableListOf <AttributeValue>()
//        parameters.add(AttributeValue.S(id.toString()))
//        val result: ExecuteStatementResponse
//        runBlocking {
//            result = executeStatementPartiQL(client, "SELECT * FROM Cards WHERE card_id=?", parameters)
//        }
//
//        return result.toCard()
        TODO("Not yet implemented")
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
        val userCards = getByUserId(userId)

        val cards = userCards.filter { card ->
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