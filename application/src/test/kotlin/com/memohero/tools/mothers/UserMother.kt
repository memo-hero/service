package com.memohero.tools.mothers

import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryStats
import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.User
import com.memohero.tools.getRandomInt
import java.util.*

fun getRandomUser(
    id: String = UUID.randomUUID().toString(),
    stats: Stats = getRandomStats(),
) = User(
    id = id,
    stats = stats,
)

fun getRandomStats(
    health: Int = 100,
    categories: List<CategoryStats> = listOf(
        getRandomCategoryStat(category = Category.ARTS),
        getRandomCategoryStat(category = Category.COMPUTERS),
        getRandomCategoryStat(category = Category.HISTORY),
        getRandomCategoryStat(category = Category.LANGUAGES),
        getRandomCategoryStat(category = Category.SCIENCE),
    )
) = Stats(
    health = health,
    categories = categories,
)

fun getRandomCategoryStat(
    category: Category = Category.values().random(),
    level: Int = getRandomInt(1, 10),
    exp: Int = getRandomInt(0, 75),
) = CategoryStats(
    category = category,
    level = level,
    exp = exp,
)