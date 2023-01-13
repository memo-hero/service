package com.memohero.core.domain.user

data class Stats(
    val health: Int = 100,
    val categories: MutableList<CategoryStats>
) {
    val generalLevel get() = calculateGeneralLevel()

    private fun calculateGeneralLevel(): Int {
        return categories.fold(0) { sum, element -> sum + element.level } / categories.count()
    }
}