package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserByIDTest {
    private val mockedRepository: UserRepository = mock()
    private val user = getRandomUser()

    @BeforeEach
    internal fun setUp() {
        whenever(mockedRepository.getById(user.id)).thenReturn(user)
    }

    @Test
    fun `should return an user by its id`() {
        val getUserById = GetUserByID(mockedRepository)

        val resultUser = getUserById(user.id)

        assertThat(resultUser.id).isEqualTo(user.id)
    }

    @Test
    fun `should throw an exception when the user is not found`() {
        val getUserById = GetUserByID(mockedRepository)

        assertThrows<UserNotFoundException> {
            getUserById("invalid id")
        }
    }
}