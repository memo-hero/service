package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.ExecuteStatementResponse
import aws.sdk.kotlin.services.dynamodb.model.GetItemResponse
import aws.sdk.kotlin.services.dynamodb.model.QueryResponse
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardStudyMetadata
import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryProperties
import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.User
import com.memohero.infrastructure.repository.dynamodb.DynamoMapper.toUser
import java.util.*

object DynamoMapper {
    fun User.toDynamoMap(): MutableMap<String, AttributeValue> {
        return mutableMapOf(
            "id" to AttributeValue.S(this.id),
            "stats" to AttributeValue.M(this.stats.toDynamoMap())
        )
    }

    fun Card.toDynamoMap(): MutableMap<String, AttributeValue> {
        return mutableMapOf(
            "card_id" to AttributeValue.S(this.id.toString()),
            "user_id" to AttributeValue.S(this.userId),
            "front" to AttributeValue.S(this.front),
            "back" to AttributeValue.S(this.back),
            "category" to AttributeValue.S(this.category.name),
            "tags" to AttributeValue.Ss(this.tags.toList()),
            "due_date" to AttributeValue.N(this.dueDate.toString()),
            "study_metadata" to AttributeValue.M(this.studyMetadata.toDynamoMap()),
        )
    }

    private fun CardStudyMetadata.toDynamoMap(): MutableMap<String, AttributeValue> {
        return mutableMapOf(
            "repetition" to AttributeValue.N(this.repetition.toString()),
            "ease_factor" to AttributeValue.N(this.easeFactor.toString()),
            "interval" to AttributeValue.N(this.interval.toString()),
        )
    }

    private fun CategoryProperties.toDynamoMap(): MutableMap<String, AttributeValue> {
        return mutableMapOf(
            "level" to AttributeValue.N(this.level.toString()),
            "exp" to AttributeValue.N(this.exp.toString())
        )
    }

    private fun Stats.toDynamoMap(): MutableMap<String, AttributeValue> {
        val categories = mutableMapOf<String, AttributeValue>()

        this.categories.forEach { category ->
            categories[category.key.name] = AttributeValue.M(category.value.toDynamoMap())
        }

        return mutableMapOf(
            "health" to AttributeValue.N(this.health.toString()),
            "categories" to AttributeValue.M(categories)
        )
    }

    fun GetItemResponse.toUser(): User? {
        return if (this.item == null) null
        else User(
            id = this.item!!["id"]!!.asS(),
            stats = this.item!!["stats"]!!.asM().toStats()
        )
    }

    fun GetItemResponse.toCardList(): List<Card> {
        return if (this == null) emptyList()
        else emptyList()
    }

    fun ExecuteStatementResponse.toCard(): Card? {
        return if (this.items == null) null
        else {
            Card(
                id = UUID.fromString(this.items!![0]["card_id"]!!.asS()),
                userId = this.items!![0]["user_id"]!!.asS(),
                front = this.items!![0]["front"]!!.asS(),
                back = this.items!![0]["back"]!!.asS(),
                category = Category.valueOf(this.items!![0]["category"]!!.asS()),
                dueDate = this.items!![0]["due_date"]!!.asN().toLong(),
                tags = this.items!![0]["tags"]!!.asSs().toMutableSet(),
                studyMetadata = this.items!![0]["study_metadata"]!!.toStudyMetadata()
            )
        }
    }

    private fun AttributeValue.toStudyMetadata(): CardStudyMetadata {
        return CardStudyMetadata(
            repetition = this.asM()["repetition"]!!.asN().toInt(),
            easeFactor = this.asM()["ease_factor"]!!.asN().toDouble(),
            interval = this.asM()["interval"]!!.asN().toLong(),
        )
    }

    private fun Map<String, AttributeValue>.toStats(): Stats {
        return Stats(
            health = this["health"]!!.asN().toInt(),
            categories = this["categories"]!!.asM().toCategories()
        )
    }

    private fun Map<String, AttributeValue>.toCategories(): MutableMap<Category, CategoryProperties> {
        val map = mutableMapOf<Category, CategoryProperties>()
        this.forEach { category ->
            map[Category.valueOf(category.key)] = category.value.asM().toCategoryProperties()
        }
        return map
    }

    private fun Map<String, AttributeValue>.toCategoryProperties(): CategoryProperties {
        return CategoryProperties(
            level = this["level"]!!.asN().toInt(),
            exp = this["exp"]!!.asN().toInt()
        )
    }
}