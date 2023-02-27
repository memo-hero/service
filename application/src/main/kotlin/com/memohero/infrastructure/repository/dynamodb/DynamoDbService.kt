package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import aws.sdk.kotlin.services.dynamodb.transactWriteItems

class DynamoDbService(
    private val client: DynamoDbClient
) {
    suspend fun dynamoPutItemRequest(dbTableName: String, dynamoItem: MutableMap<String, AttributeValue>) {
        val request = PutItemRequest {
            tableName = dbTableName
            item = dynamoItem
        }

        client.putItem(request)
    }

    suspend fun dynamoQueryRequest(
        dbTableName: String,
        expression: String,
        attributeNames: Map<String, String>?,
        attributeValues: Map<String, AttributeValue>?
    ): QueryResponse {
        val request = QueryRequest {
            tableName = dbTableName
            keyConditionExpression = expression
            expressionAttributeNames = attributeNames
            this.expressionAttributeValues = attributeValues
        }

        return client.query(request)
    }

    suspend fun dynamoQueryTable(
        queryStatement: String,
        queryParameters: List<AttributeValue>
    ): ExecuteStatementResponse {
        val request = ExecuteStatementRequest {
            statement = queryStatement
            parameters = queryParameters
        }

        return client.executeStatement(request)
    }

    suspend fun dynamoGetItemRequest(dbTableName: String, keyName: String, keyValue: AttributeValue): GetItemResponse {
        val request = GetItemRequest {
            key = mutableMapOf(keyName to keyValue)
            tableName = dbTableName
        }

        return client.getItem(request)
    }

    suspend fun dynamoDeleteItemRequest(
        dbTableName: String,
        keyName: String,
        keyValue: String,
        sortKeyName: String,
        sortKeyValue: String
    ): DeleteItemResponse {
        val request = DeleteItemRequest {
            tableName = dbTableName
            key = mutableMapOf(
                keyName to AttributeValue.S(keyValue),
                sortKeyName to AttributeValue.S(sortKeyValue)
            )
        }

        return client.deleteItem(request)
    }

    suspend fun makePutTransaction(
        table1: String,
        item1: Map<String, AttributeValue>,
        table2: String,
        item2: Map<String, AttributeValue>
    ) {
        client.transactWriteItems {
            transactItems = listOf(
                TransactWriteItem {
                    put {
                        tableName = table1
                        item = item1
                    }
                },
                TransactWriteItem {
                    put {
                        tableName = table2
                        item = item2
                    }

                }
            )
        }
    }
}