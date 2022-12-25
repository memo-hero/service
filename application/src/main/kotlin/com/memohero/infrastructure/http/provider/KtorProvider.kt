package com.memohero.infrastructure.http.provider;

import com.memohero.core.action.cards.GetCards
import com.memohero.core.action.cards.GetVersion
import com.memohero.core.action.cards.StoreCard
import com.memohero.core.domain.card.exceptions.InvalidParameterException
import com.memohero.infrastructure.http.Path
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
        get(Path.GET_VERSION) {
            call.respond(getVersionAction())
        }
    }

    private fun Route.storeCard(storeCardAction: StoreCard) {
        post(Path.STORE_CARD) {
            val card =  call.receive<CardJson>()
            storeCardAction(card.toCard())
        }
    }

    private fun Route.getCards(getCardsAction: GetCards) {
        get(Path.GET_CARDS) {
            val userId = call.parameters["userId"] ?: throw InvalidParameterException("Missing parameter userId")
            call.respond(getCardsAction(userId))
        }
    }
}
