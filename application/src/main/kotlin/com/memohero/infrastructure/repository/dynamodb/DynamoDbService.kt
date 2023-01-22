package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.QueryRequest
import aws.sdk.kotlin.services.dynamodb.model.QueryResponse

class DynamoDbService(
    private val client: DynamoDbClient
) {
    suspend fun dynamoPutItemRequest(dbTableName: String, dynamoItem:  MutableMap<String, AttributeValue>) {
        val request = PutItemRequest {
            tableName = dbTableName
            item = dynamoItem
        }

        client.putItem(request)
    }

    suspend fun dynamoQueryRequest(dbTableName: String, expression: String, attributeNames:  Map<String, String>?, attributeValues:  Map<String, AttributeValue>?): QueryResponse {
        val request = QueryRequest {
            tableName = dbTableName
            keyConditionExpression = expression
            expressionAttributeNames = attributeNames
            this.expressionAttributeValues = attributeValues
        }

        return client.query(request)
    }
}