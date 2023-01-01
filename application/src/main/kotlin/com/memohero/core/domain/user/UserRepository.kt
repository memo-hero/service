package com.memohero.core.domain.user

interface UserRepository {
    fun storeUser(user: User)
    fun getById(id: String): User
    fun checkUserExists(user: User): Boolean
}