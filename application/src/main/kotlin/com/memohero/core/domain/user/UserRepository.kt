package com.memohero.core.domain.user

interface UserRepository {
    fun storeUser(user: User)
    fun checkUserExists(user: User): Boolean
}