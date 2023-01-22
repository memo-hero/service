package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class GetUserByID(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String): User {
        return userRepository.getById(id) ?: throw UserNotFoundException(id)
    }
}