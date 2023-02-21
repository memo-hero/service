package com.memohero.infrastructure.http

interface IHttpClient {
    suspend fun makePost(url: String, content: String): Boolean
    suspend fun makeGet(url: String, headers: Pair<String, String>): String
}