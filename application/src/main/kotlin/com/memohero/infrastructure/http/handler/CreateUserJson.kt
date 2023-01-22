package com.memohero.infrastructure.http.handler

import com.memohero.core.domain.user.User

data class CreateUserJson(
    val id: String?
) {
    fun toUser(): User {
        return if (id == null) User()
        else User(id = id)
    }
}