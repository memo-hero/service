package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.GetCards
import com.memohero.core.action.cards.StoreCard
import com.memohero.core.domain.exceptions.CardAlreadyExistsException
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.CardJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storeCard(storeCardAction: StoreCard) {
    post(Path.STORE_CARD) {
        try {
            val card =  call.receive<CardJson>()
            storeCardAction(card.toCard())
        }
        catch (ex: CardAlreadyExistsException) {
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.getCards(getCardsAction: GetCards) {
    get(Path.GET_CARDS) {
        try {
            val userId = call.parameters["userId"] ?: throw InvalidParameterException("Missing parameter userId")
            call.respond(getCardsAction(userId))
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}