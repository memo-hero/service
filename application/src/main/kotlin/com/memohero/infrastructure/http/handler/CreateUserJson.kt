package com.memohero.infrastructure.http.handler

import com.memohero.core.domain.user.User

data class CreateUserJson(
    val user_id: String
) {
    fun toUser() = User(id = user_id)
}