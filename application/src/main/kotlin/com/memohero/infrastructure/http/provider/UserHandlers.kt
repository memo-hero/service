package com.memohero.infrastructure.http.provider

import com.memohero.core.action.users.CreateUser
import com.memohero.core.action.users.GetUserByID
import com.memohero.core.action.users.UpdateUser
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.core.domain.exceptions.UserAlreadyExistsException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.logging.LogSeverity
import com.memohero.infrastructure.Services
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.CreateUserJson
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.logging.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import kotlinx.coroutines.GlobalScope

fun Route.updateUser(updateUserAction: UpdateUser) {
    put(Path.UPDATE_USER) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val user = call.receive<UpdateUserJson>()
            val userId = call.parameters["user_id"] ?: throw InvalidParameterException("Missing parameter userId")
            updateUserAction(user.toUser(userId))

            logResponse(call)
            call.respond(HttpStatusCode.OK)
        }
        catch (ex: UserNotFoundException) {
            Services.loggerService.log(ex.message!!, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message)
        }
        catch (ex: Exception) {
            Services.loggerService.log(ex.message!!, LogSeverity.ERROR)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.createUser(createUserAction: CreateUser) {
    post(Path.CREATE_USER) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.parameters["user_id"] ?: throw InvalidParameterException("Missing parameter userId")
            val newUser = CreateUserJson(userId).toUser()
            createUserAction(newUser)

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(newUser)
        }
        catch (ex: UserAlreadyExistsException) {
            Services.loggerService.log(ex.message!!, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: Exception) {
            Services.loggerService.log(ex.message!!, LogSeverity.ERROR)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.getUserByID(getById: GetUserByID) {
    get(Path.GET_USER_BY_ID) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.parameters["user_id"] ?: throw InvalidParameterException("Missing parameter userId")
            val user = getById(userId)

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(user)
        }
        catch (ex: InvalidParameterException) {
            Services.loggerService.log(ex.message, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: UserNotFoundException) {
            Services.loggerService.log(ex.message!!, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message)
        }
        catch (ex: Exception) {
            Services.loggerService.log(ex.message!!, LogSeverity.ERROR)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

private fun logResponse(call: ApplicationCall) {
    val phase = PipelinePhase("phase")
    call.response.pipeline.insertPhaseBefore(ApplicationSendPipeline.Engine, phase)
    call.response.pipeline.intercept(phase) { response ->
        val content: ByteReadChannel = when (response) {
            is OutgoingContent.ByteArrayContent -> ByteReadChannel(response.bytes())
            is OutgoingContent.NoContent -> ByteReadChannel.Empty
            is OutgoingContent.ReadChannelContent -> response.readFrom()
            is OutgoingContent.WriteChannelContent -> GlobalScope.writer(coroutineContext, autoFlush = true) {
                response.writeTo(channel)
            }.channel
            else -> error("")
        }
        content.readUTF8Line()?.let {
            Services.loggerService.log("response=$it")
        }
    }
}