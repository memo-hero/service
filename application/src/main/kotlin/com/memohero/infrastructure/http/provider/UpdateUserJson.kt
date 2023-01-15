package com.memohero.infrastructure.http.provider

import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.User

data class UpdateUserJson(
    val id: String,
    val stats: Stats,
) {
    fun toUser() = User(
        id = id,
        stats = stats,
    )
}