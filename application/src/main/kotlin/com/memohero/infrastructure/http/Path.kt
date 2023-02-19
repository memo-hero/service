package com.memohero.infrastructure.http

object Path {
    const val GET_VERSION = "/version"
    const val PUSH_LOGS = "/users/{user_id}/logs"

    const val CREATE_USER = "/users"
    const val GET_USER_BY_ID = "/users/{user_id}"
    const val UPDATE_USER = "/users/{user_id}"

    const val STORE_CARD = "/users/{user_id}/cards"
    const val UPDATE_CARD = "/users/{user_id}/cards/{card_id}"
    const val GET_CARDS = "/users/{user_id}/cards"
    const val GET_DUE_CARDS = "/users/{user_id}/cards/due"
    const val GET_CARDS_BY_ID = "/users/{user_id}/cards/{card_id}"
    const val GET_CARDS_BY_TAGS = "/users/{user_id}/cards/tag"
    const val DELETE_CARD = "/users/{user_id}/cards/{card_id}"

    const val STUDY_CARD = "/users/{user_id}/cards/{card_id}/study"
}
