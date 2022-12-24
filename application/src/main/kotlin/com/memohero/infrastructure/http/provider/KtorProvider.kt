package com.memohero.infrastructure.http.provider;

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

object KtorProvider {
    fun start() {
        val server = embeddedServer(Netty, port = 8080) {
            routing {
                get("/") {
                    call.respondText("Hello World!", ContentType.Text.Plain)
                }
                get("/test1") {
                    call.respondText { "This is the first test" }
                }
            }
        }
        server.start(wait = true)
    }
}
