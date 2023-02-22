package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.*
import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardAnswer
import com.memohero.core.domain.exceptions.CardAlreadyExistsException
import com.memohero.core.domain.exceptions.CardNotFoundException
import com.memohero.core.domain.exceptions.InvalidParameterException
import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.logging.LogSeverity
import com.memohero.infrastructure.Services
import com.memohero.infrastructure.http.Path
import com.memohero.infrastructure.http.handler.NewCardJson
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
import java.util.*

fun Route.storeCard(storeCardAction: StoreCard) {
    post(Path.STORE_CARD) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val card =  call.receive<NewCardJson>().toCard(userId = userId)
            storeCardAction(card)

            logResponse(call)
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
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val card =  call.receive<Card>()
            updateCardAction(card)

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
        }
        catch (ex: CardNotFoundException) {
            Services.loggerService.log(ex.message!!, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.NotFound)
            call.respond(ex.message!!)
        }
        catch (ex: Exception) {
            Services.loggerService.log(ex.message!!, LogSeverity.ERROR)
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(ex.message!!)
        }
    }
}

fun Route.getCards(getCardsAction: GetCards) {
    get(Path.GET_CARDS) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(getCardsAction(userId))
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

fun Route.getDueCards(getDueCards: GetDueCards) {
    get(Path.GET_DUE_CARDS) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val tags = call.parameters.getAll("tag")?.toSet() ?: emptySet()

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(getDueCards(userId, tags))
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

fun Route.getCardById(getCardById: GetCardById) {
    get(Path.GET_CARDS_BY_ID) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(getCardById(userId, cardId))
        }
        catch (ex: CardNotFoundException) {
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

fun Route.getCardsByTags(getCardsByTag: GetCardsByTag) {
    get(Path.GET_CARDS_BY_TAGS) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val tags = call.parameters.getAll("tag") ?: throw InvalidParameterException("Missing parameter tags")

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(getCardsByTag(userId, tags.toMutableSet()))
        }
        catch (ex: InvalidParameterException) {
            Services.loggerService.log(ex.message, LogSeverity.WARNING)
            call.response.status(HttpStatusCode.BadRequest)
            call.respond(ex.message)
        }
        catch (ex: CardNotFoundException) {
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

fun Route.studyCard(studyCard: StudyCard) {
    post(Path.STUDY_CARD) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")
            val quality = call.getQueryParameter("quality").toInt()
            val result = studyCard(CardAnswer(userId, UUID.fromString(cardId), quality))

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(result)
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

fun Route.deleteCard(deleteCard: DeleteCard) {
    delete(Path.DELETE_CARD) {
        try {
            Services.loggerService.log(call.request.toLogString())
            val userId = call.getParameter("user_id")
            val cardId = call.getParameter("card_id")
            val result = deleteCard(userId, cardId)

            logResponse(call)
            call.response.status(HttpStatusCode.OK)
            call.respond(result)
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

private fun ApplicationCall.getParameter(parameterName: String) = this.parameters[parameterName] ?: throw InvalidParameterException("Missing parameter: $parameterName")
private fun ApplicationCall.getQueryParameter(parameterName: String) = this.request.queryParameters[parameterName] ?: throw InvalidParameterException("Missing parameter: $parameterName")

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