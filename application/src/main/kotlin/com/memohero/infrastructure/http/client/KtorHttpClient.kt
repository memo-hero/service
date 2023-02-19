package com.memohero.infrastructure.http.client

import com.memohero.infrastructure.http.IHttpClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorHttpClient: IHttpClient {
    private val client = HttpClient()

    override suspend fun makePost(url: String, content: String): Boolean {
        val result = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(content)
        }

        return result.status.isSuccess()
    }
}