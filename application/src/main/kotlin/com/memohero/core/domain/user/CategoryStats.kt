package com.memohero.core.domain.user

data class CategoryStats(
    val category: Category,
    val level: Int = 0,
    val exp: Int = 0,
)