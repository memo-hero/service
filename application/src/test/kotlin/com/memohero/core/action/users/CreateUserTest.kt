package com.memohero.core.action.users

import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CreateUserTest {
    private val mockedRepository: UserRepository = mock()

    @Test
    fun `Should store an user`() {
        val createUser = CreateUser(mockedRepository)
        val user = User(id = "Some ID")

        createUser(user)

        verify(mockedRepository, times(1)).storeUser(user)
    }
}