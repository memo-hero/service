package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.*
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.exceptions.CardAlreadyExistsException
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.NewCardJson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.storeCard(storeCardAction: StoreCard) {
    post(Path.STORE_CARD) {
        try {
            val userId = call.getParameter("user_id")
            val card =  call.receive<NewCardJson>().toCard(userId = userId)
            storeCardAction(card)

            call.response.status(HttpStatusCode.OK)
            call.respond(card)
        }
        catch (ex: CardAlreadyExistsException) {
            print(ex.message)
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: Exception) {
            print(ex.message)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.updateCard(updateCardAction: UpdateCard) {
    post(Path.UPDATE_CARD) {
        try {
            val userId = call.getParameter("user_id")
            val card =  call.receive<Card>()
            updateCardAction(card)

            call.response.status(HttpStatusCode.OK)
        }
        catch (ex: CardNotFoundException) {
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message!!)
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
            val userId = call.getParameter("user_id")
            call.respond(getCardsAction(userId))
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

fun Route.getDueCards(getDueCards: GetDueCards) {
    get(Path.GET_DUE_CARDS) {
        try {
            val userId = call.getParameter("user_id")
            val tags = call.parameters.getAll("tag")?.toSet() ?: emptySet()
            call.respond(getDueCards(userId, tags))
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

fun Route.getCardById(getCardById: GetCardById) {
    get(Path.GET_CARDS_BY_ID) {
        try {
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")
            call.respond(getCardById(userId, cardId))
        }
        catch (ex: CardNotFoundException) {
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.getCardsByTags(getCardsByTag: GetCardsByTag) {
    get(Path.GET_CARDS_BY_TAGS) {
        try {
            val userId = call.getParameter("user_id")
            val tags = call.parameters.getAll("tag") ?: throw InvalidParameterException("Missing parameter tags")
            call.respond(getCardsByTag(userId, tags.toMutableSet()))
        }
        catch (ex: InvalidParameterException) {
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: CardNotFoundException) {
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.studyCard(studyCard: StudyCard) {
    post(Path.STUDY_CARD) {
        try {
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")
            val quality = call.getQueryParameter("quality").toInt()

            val result = studyCard(CardAnswer(userId, UUID.fromString(cardId), quality))
            call.respond(result)
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

fun Route.deleteCard(deleteCard: DeleteCard) {
    delete(Path.DELETE_CARD) {
        try {
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")

            val result = deleteCard(userId, cardId)
            call.respond(result)
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

private fun ApplicationCall.getParameter(parameterName: String) = this.parameters[parameterName] ?: throw InvalidParameterException("Missing parameter: $parameterName")
private fun ApplicationCall.getQueryParameter(parameterName: String) = this.request.queryParameters[parameterName] ?: throw InvalidParameterException("Missing parameter: $parameterName")