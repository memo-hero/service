package com.memohero.core.domain.user

data class User(
    val id: String,
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