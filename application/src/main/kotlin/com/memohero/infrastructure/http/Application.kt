package com.memohero.infrastructure.http

import com.memohero.infrastructure.Services
import com.memohero.infrastructure.http.provider.KtorProvider
import kotlinx.coroutines.runBlocking

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking { Services.loggerService.log("Starting service") }
        KtorProvider.start()
    }
}