package com.memohero.infrastructure.http.provider

import cc.rbbl.ktor_health_check.Health
import com.memohero.core.action.system.GetVersion
import com.memohero.infrastructure.http.Path
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
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
            call.respond(getVersionAction())
        }
    }
}