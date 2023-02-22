package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserAlreadyExistsException
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class CreateUser(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        if (!userRepository.checkUserExists(user))
            userRepository.storeUser(user)
        else
            throw UserAlreadyExistsException(user.id)
    }
}