package com.memohero.core.domain.user

data class Stats(
    val health: Int = 100,
    val categories: MutableMap<Category, CategoryProperties> = mutableMapOf(
        Category.ARTS to CategoryProperties(),
        Category.COMPUTERS to CategoryProperties(),
        Category.HISTORY to CategoryProperties(),
        Category.LANGUAGES to CategoryProperties(),
        Category.SCIENCE to CategoryProperties(),
    )
) {
    val generalLevel get() = calculateGeneralLevel()

    private fun calculateGeneralLevel(): Int {
        return Category.values().fold(0) { sum, category -> sum + categories[category]!!.level } / categories.count()
    }
}