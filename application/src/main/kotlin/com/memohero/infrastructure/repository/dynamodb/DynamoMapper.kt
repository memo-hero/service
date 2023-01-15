package com.memohero.infrastructure.repository.dynamodb

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemResponse
import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryProperties
import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.User

object DynamoMapper {
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

    fun User.toDynamoMap(): MutableMap<String, AttributeValue> {
        return mutableMapOf(
            "id" to AttributeValue.S(this.id),
            "stats" to AttributeValue.M(this.stats.toDynamoMap())
        )
    }

    fun GetItemResponse.toUser(): User? {
        return if (this.item == null) null
        else User(
            id = this.item!!["id"]!!.asS(),
            stats = this.item!!["stats"]!!.asM().toStats()
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