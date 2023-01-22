package com.memohero.tools

fun getRandomString(length: Int = 5): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun getRandomInt(min: Int = 0, max: Int = 10) = (min..max).random()