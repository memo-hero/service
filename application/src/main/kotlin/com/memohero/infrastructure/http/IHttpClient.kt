package com.memohero.infrastructure.http

interface IHttpClient {
    suspend fun makePost(url: String, content: String): Boolean
}