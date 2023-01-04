package com.memohero.core.domain.user

data class User (
    val id: String,
    val stats: Stats = Stats(listOf(
        CategoryStats(type = Category.ARTS),
        CategoryStats(type = Category.COMPUTERS),
        CategoryStats(type = Category.HISTORY),
        CategoryStats(type = Category.LANGUAGES),
        CategoryStats(type = Category.SCIENCE),
    )),
)