package com.memohero.infrastructure

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.memohero.infrastructure.repository.dynamodb.DynamoDbService

object Services {
    val dynamoDbService: DynamoDbService by lazy {
        val dynamoRegion = "sa-east-1"
        val client = DynamoDbClient { region = dynamoRegion }
        DynamoDbService(client)
    }
}