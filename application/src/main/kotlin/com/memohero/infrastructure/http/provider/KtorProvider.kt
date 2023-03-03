package com.memohero.infrastructure.http.provider

import cc.rbbl.ktor_health_check.Health
import com.memohero.core.action.system.GetVersion
import com.memohero.core.action.system.PushLogs
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.core.domain.logging.Log
import com.memohero.core.domain.logging.LogSeverity
import com.memohero.infrastructure.Services
import com.memohero.infrastructure.http.Path
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.logging.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

object KtorProvider {
    fun start() {
        val server = embeddedServer(Netty, port = 8080) {
            install(ContentNegotiation) {
                jackson()
            }
            install(Health)
            routing {
                getVersion(Actions.getVersion)
                pushLogs(Actions.pushLogs)

                storeCard(Actions.storeCard)
                updateCard(Actions.updateCard)
                getDueCards(Actions.getDueCards)
                getCards(Actions.getCards)
                getCardById(Actions.getCardById)
                getCardsByTags(Actions.getCardsByTag)
                deleteCard(Actions.deleteCard)

                createUser(Actions.createUser)
                getUserByID(Actions.getUserByID)
                updateUser(Actions.updateUser)

                studyCard(Actions.studyCard)
            }
        }
        server.start(wait = true)
    }

    private fun Route.getVersion(getVersionAction: GetVersion) {
        get(Path.GET_VERSION) {
            Services.loggerService.log(call.request.toLogString())
            call.respond(getVersionAction())
        }
    }

    private fun Route.pushLogs(pushLogsActions: PushLogs) {
        post(Path.PUSH_LOGS) {
            try {
                Services.loggerService.log(call.request.toLogString())
                val userId = call.parameters["user_id"] ?: throw InvalidParameterException("Missing parameter userId")
                val logs = call.receive<List<Log>>()
                pushLogsActions(userId, logs)
                call.response.status(HttpStatusCode.OK)
            }
            catch (ex: Exception) {
                val message = "${ ex.message ?: "Error when pushing logs" } stacktrace=${ ex.stackTraceToString() }"
                Services.loggerService.log(message = message, severity = LogSeverity.ERROR)
                call.response.status(HttpStatusCode.InternalServerError)
            }
        }
    }
}