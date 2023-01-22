package com.memohero.infrastructure.http

object Path {
    const val GET_VERSION = "/version"

    const val CREATE_USER = "/users"
    const val GET_USER_BY_ID = "/users/{user_id}"
    const val UPDATE_USER = "/users/{user_id}"

    const val STORE_CARD = "/users/{user_id}/cards"
    const val GET_CARDS = "/users/{user_id}/cards"
}