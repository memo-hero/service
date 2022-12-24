package com.memohero.infrastructure.http.provider;

import com.memohero.core.action.GetCards
import com.memohero.core.action.GetVersion
import com.memohero.core.action.StoreCard
import com.memohero.core.domain.card.exceptions.InvalidParameterException
import com.memohero.core.domain.card.exceptions.UserNotFoundException
import com.memohero.infrastructure.http.handler.CardJson
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
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
            routing {
                getVersion(Actions.getVersion)
                storeCard(Actions.storeCard)
                getCards(Actions.getCards)
            }
        }
        server.start(wait = true)
    }

    private fun Route.getVersion(getVersionAction: GetVersion) {
        get("/version") {
            call.respond(getVersionAction())
        }
    }

    private fun Route.storeCard(storeCardAction: StoreCard) {
        post("/card") {
            val card =  call.receive<CardJson>()
            storeCardAction(card.toCard())
        }
    }

    private fun Route.getCards(getCardsAction: GetCards) {
        get("/card") {
            val userId = call.parameters["userId"] ?: throw InvalidParameterException("Missing parameter userId")
            call.respond(getCardsAction(userId))
        }
    }
}
