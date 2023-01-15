package com.memohero.infrastructure.http.provider

import com.memohero.core.action.users.CreateUser
import com.memohero.core.action.users.GetUserByID
import com.memohero.core.action.users.UpdateUser
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.core.domain.exceptions.UserAlreadyExistsException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.CreateUserJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

fun Route.updateUser(updateUserAction: UpdateUser) {
    put(Path.UPDATE_USER) {
        try {
            val user = call.receive<UpdateUserJson>()
            updateUserAction(user.toUser())
        }
        catch (ex: UserNotFoundException) {
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.createUser(createUserAction: CreateUser) {
    post(Path.CREATE_USER) {
        try {
            val user = call.receive<CreateUserJson>()
            createUserAction(user.toUser())

            call.response.status(HttpStatusCode.OK)
            call.respond(user.toUser())
        }
        catch (ex: UserAlreadyExistsException) {
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.getUserByID(getById: GetUserByID) {
    get(Path.GET_USER_BY_ID) {
        try {
            val userId = call.parameters["userId"] ?: throw InvalidParameterException("Missing parameter userId")
            val user = getById(userId)

            call.response.status(HttpStatusCode.OK)
            call.respond(user)
        }
        catch (ex: InvalidParameterException) {
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: UserNotFoundException) {
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}