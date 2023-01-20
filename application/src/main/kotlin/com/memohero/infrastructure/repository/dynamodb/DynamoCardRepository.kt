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
        val result: GetItemResponse
        val request = GetItemRequest {
            key = mutableMapOf<String, AttributeValue>("card_id" to AttributeValue.S(id))
            tableName = dbTableName
        }

        runBlocking {
            result = client.getItem(request)
        }

        return result.toCardList()
    }

    override fun getById(id: UUID): Card? {
        val parameters = mutableListOf <AttributeValue>()
        parameters.add(AttributeValue.S(id.toString()))
        val result: ExecuteStatementResponse
        runBlocking {
            result = executeStatementPartiQL(client, "SELECT * FROM Cards WHERE card_id=?", parameters)
            print(result)
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
        TODO("Not yet implemented")
    }

    override fun update(card: Card) {
        TODO("Not yet implemented")
    }
}