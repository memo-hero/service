package com.memohero.core.domain.user

data class User (
    val id: String,
    val stats: Stats = Stats(
        categories = listOf(
            CategoryStats(category = Category.ARTS),
            CategoryStats(category = Category.COMPUTERS),
            CategoryStats(category = Category.HISTORY),
            CategoryStats(category = Category.LANGUAGES),
            CategoryStats(category = Category.SCIENCE),
    )),
)