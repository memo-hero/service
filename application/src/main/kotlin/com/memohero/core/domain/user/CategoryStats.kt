package com.memohero.core.domain.user

data class CategoryStats(
    val type: Category,
    val level: Int = 0,
    val exp: Int = 0,
)