package com.memohero.infrastructure.http

import com.memohero.infrastructure.http.provider.KtorProvider

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        KtorProvider.start()
    }
}