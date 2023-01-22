package com.memohero.infrastructure.repository

import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class InMemoryUserRepository: UserRepository {
    override fun storeUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun getById(id: String): User? {
        TODO("Not yet implemented")
    }

    override fun checkUserExists(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}