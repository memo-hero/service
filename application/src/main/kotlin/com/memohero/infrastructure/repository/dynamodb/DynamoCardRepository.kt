package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.ExecuteStatementResponse
import aws.sdk.kotlin.services.dynamodb.model.QueryResponse
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.infrastructure.Services
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toCard
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toCardList
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toDynamoMap
import java.util.*

class DynamoCardRepository(
    private val dynamoService: DynamoDbService
): CardRepository {
    private val dbTableName = "Cards"

    override suspend fun add(card: Card) {
        Services.loggerService.log("adding_card=$card")
        dynamoService.dynamoPutItemRequest(dbTableName, card.toDynamoMap())
    }

    override suspend fun getByUserId(id: String): List<Card> {
        Services.loggerService.log("getting_card_by_user=${id}")
        val partitionKeyName = "user_id"
        val attributeAlias = "#a"
        val attributeNames = mutableMapOf(attributeAlias to partitionKeyName)
        val attributeValues = mutableMapOf<String, AttributeValue>(":$partitionKeyName" to AttributeValue.S(id))
        val keyConditionExpression = "$attributeAlias = :$partitionKeyName"

        val result: QueryResponse =
            dynamoService.dynamoQueryRequest(dbTableName, keyConditionExpression, attributeNames, attributeValues)

        return result.toCardList()
    }

    override suspend fun getById(id: UUID): Card? {
        Services.loggerService.log("getting_card_by_id=${id}")
        // Should avoid using this call due to its impact on DynamoDB
        val queryStatement = "SELECT * FROM $dbTableName WHERE card_id=?"
        val queryParameters = mutableListOf(AttributeValue.S(id.toString()))

        val result: ExecuteStatementResponse = dynamoService.dynamoQueryTable(queryStatement, queryParameters)

        return result.toCard()
    }

    override suspend fun getByTags(userId: String, tags: Set<String>): List<Card> {
        Services.loggerService.log("getting_cards_by_tags=${tags}")

        val userCards = getByUserId(userId)

        val cards = userCards.filter { card ->
            card.tags.any { it in tags }
        }

        return cards
    }

    override suspend fun update(card: Card) {
        Services.loggerService.log("updating_card=${card}")
        dynamoService.dynamoPutItemRequest(dbTableName, card.toDynamoMap())
    }

    override suspend fun deleteCard(userId: String, cardId: String) {
        Services.loggerService.log("deleting_card=${cardId}")
        dynamoService.dynamoDeleteItemRequest(dbTableName, "card_id", cardId, "user_id", userId)
    }
}