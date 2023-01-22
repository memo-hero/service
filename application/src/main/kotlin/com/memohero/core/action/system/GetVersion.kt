package com.memohero.core.action.system

import java.util.*

class GetVersion {
    private val versionProperties = Properties()

    init {
        versionProperties.load(this.javaClass.getResourceAsStream("/version.properties"))
    }

    operator fun invoke() = versionProperties.getProperty("version") ?: "no version"
}