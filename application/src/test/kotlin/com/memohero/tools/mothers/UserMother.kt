package com.memohero.tools.mothers

import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryProperties
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
    categories: MutableMap<Category, CategoryProperties> = mutableMapOf(
        Category.ARTS to getRandomCategoryProperties(),
        Category.COMPUTERS to getRandomCategoryProperties(),
        Category.HISTORY to getRandomCategoryProperties(),
        Category.LANGUAGES to getRandomCategoryProperties(),
        Category.SCIENCE to getRandomCategoryProperties(),
    )
) = Stats(
    health = health,
    categories = categories,
)

fun getRandomCategoryProperties(
    level: Int = getRandomInt(1, 10),
    exp: Int = getRandomInt(0, 75),
) = CategoryProperties(
    level = level,
    exp = exp,
)