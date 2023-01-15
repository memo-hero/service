package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.GetCards
import com.memohero.core.action.cards.StoreCard
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.CardJson
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storeCard(storeCardAction: StoreCard) {
    post(Path.STORE_CARD) {
        val card =  call.receive<CardJson>()
        storeCardAction(card.toCard())
    }
}

fun Route.getCards(getCardsAction: GetCards) {
    get(Path.GET_CARDS) {
        val userId = call.parameters["userId"] ?: throw InvalidParameterException("Missing parameter userId")
        call.respond(getCardsAction(userId))
    }
}