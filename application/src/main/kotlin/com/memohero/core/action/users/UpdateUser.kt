package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class UpdateUser(
    private val userRepository: UserRepository
) {
    operator fun invoke(user: User) {
        if (userRepository.checkUserExists(user))
            userRepository.updateUser(user)
        else throw UserNotFoundException("User with ${user.id} was not found")
    }
}