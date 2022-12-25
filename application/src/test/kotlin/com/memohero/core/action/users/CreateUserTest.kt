package com.memohero.core.action.users

import com.memohero.core.domain.user.User
import com.memohero.core.domain.user.UserRepository
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreateUserTest {
    private val mockedRepository: UserRepository = mock()
    private val user = User(id = "Some ID")

    @Test
    fun `Should store an user`() {
        val createUser = CreateUser(mockedRepository)

        createUser(user)

        verify(mockedRepository, times(1)).checkUserExists(user)
        verify(mockedRepository, times(1)).storeUser(user)
    }

    @Test
    fun `Should not store an user if it is already stored`() {
        val createUser = CreateUser(mockedRepository)
        whenever(mockedRepository.checkUserExists(user)).thenReturn(true)

        createUser(user)

        verify(mockedRepository, times(1)).checkUserExists(user)
        verify(mockedRepository, times(0)).storeUser(user)
    }
}