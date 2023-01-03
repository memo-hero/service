package com.memohero.tools

import com.memohero.core.domain.user.User
import java.util.*

object UserMother {
    fun getUser(
        id: String = UUID.randomUUID().toString()
    ) = User(
        id = id
    )
}