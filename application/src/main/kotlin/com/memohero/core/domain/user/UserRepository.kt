package com.memohero.core.domain.user

import com.memohero.core.domain.card.Card

interface UserRepository {
    suspend fun storeUser(user: User)
    suspend fun getById(id: String): User?
    suspend fun checkUserExists(user: User): Boolean
    suspend fun updateUser(user: User)
    suspend fun makePutTransaction(user: User, card: Card)
}