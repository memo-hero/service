package com.memohero.infrastructure.http.provider

import com.memohero.core.action.users.CreateUser
import com.memohero.core.action.users.UpdateUser
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.CreateUserJson
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.updateUser(updateUserAction: UpdateUser) {
    put(Path.UPDATE_USER) {
        val user = call.receive<CreateUserJson>()
        updateUserAction(user.toUser())
    }
}

fun Route.createUser(createUserAction: CreateUser) {
    post(Path.CREATE_USER) {
        val user = call.receive<CreateUserJson>()
        createUserAction(user.toUser())
    }
}