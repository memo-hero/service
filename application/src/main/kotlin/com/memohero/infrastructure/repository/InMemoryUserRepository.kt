package com.memohero.infrastructure.repository

import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class InMemoryUserRepository: UserRepository {
    override suspend fun storeUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun checkUserExists(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }
}