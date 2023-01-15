package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.GetVersion
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
            routing {
                getVersion(Actions.getVersion)

                storeCard(Actions.storeCard)
                getCards(Actions.getCards)

                createUser(Actions.createUser)
                updateUser(Actions.updateUser)
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