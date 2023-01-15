package com.memohero.core.domain.user

import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val stats: Stats = Stats(
        categories = mutableMapOf(
            Category.ARTS to CategoryProperties(),
            Category.COMPUTERS to CategoryProperties(),
            Category.HISTORY to CategoryProperties(),
            Category.LANGUAGES to CategoryProperties(),
            Category.SCIENCE to CategoryProperties(),
        )
    ),
)