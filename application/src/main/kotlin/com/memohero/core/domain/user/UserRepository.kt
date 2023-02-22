package com.memohero.core.domain.user

interface UserRepository {
    suspend fun storeUser(user: User)
    suspend fun getById(id: String): User?
    suspend fun checkUserExists(user: User): Boolean
    suspend fun updateUser(user: User)
}