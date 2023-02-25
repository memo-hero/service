package com.memohero.infrastructure.repository

import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository

class InMemoryUserRepository: UserRepository {
    private val storedUsers = mutableListOf<User>()

    override suspend fun storeUser(user: User) {
        storedUsers.add(user)
    }

    override suspend fun getById(id: String)
        = storedUsers.find { user -> user.id == id }

    override suspend fun checkUserExists(user: User)
        = getById(user.id) != null

    override suspend fun updateUser(user: User) {
        storedUsers.remove(getById(user.id))
        storedUsers.add(user)
    }
}