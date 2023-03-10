package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserAlreadyExistsException
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreateUserTest {
    private val mockedRepository: UserRepository = mock()
    private val user = getRandomUser()

    @BeforeEach
    internal suspend fun setUp() {
        whenever(mockedRepository.checkUserExists(user)).thenReturn(false)
    }

    @Test
    suspend fun `Should store an user`() {
        val createUser = CreateUser(mockedRepository)

        createUser(user)

        verify(mockedRepository, times(1)).checkUserExists(user)
        verify(mockedRepository, times(1)).storeUser(user)
    }

    @Test
    suspend fun `Should not store an user if it is already stored`() {
        val createUser = CreateUser(mockedRepository)
        whenever(mockedRepository.checkUserExists(user)).thenReturn(true)

        assertThrows<UserAlreadyExistsException> {
            createUser(user)
        }
    }
}