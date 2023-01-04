package com.memohero.tools

import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryStats
import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.User
import java.util.*

object UserMother {
    fun getUser(
        id: String = UUID.randomUUID().toString(),
        stats: Stats = StatsMother.getStats(),
    ) = User(
        id = id,
        stats = stats,
    )
}

object StatsMother {
    fun getStats(
        categories: List<CategoryStats> = listOf(
            CategoryStatsMother.getCategoryStat(type = Category.ARTS),
            CategoryStatsMother.getCategoryStat(type = Category.COMPUTERS),
            CategoryStatsMother.getCategoryStat(type = Category.HISTORY),
            CategoryStatsMother.getCategoryStat(type = Category.LANGUAGES),
            CategoryStatsMother.getCategoryStat(type = Category.SCIENCE),
        )
    ) = Stats(
        categories = categories,
    )
}

object CategoryStatsMother {
    fun getCategoryStat(
        type: Category = Category.values().random(),
        level: Int = getRandomInt(1, 10),
        exp: Int = getRandomInt(0, 75),
    ) = CategoryStats(
        type = type,
        level = level,
        exp = exp,
    )
}