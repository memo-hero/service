package com.memohero.core.action.users

import com.memohero.core.domain.exceptions.UserNotFoundException
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.getRandomString
import com.memohero.tools.mothers.getRandomUser
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateUserTest {
    private val mockedUserRepository: UserRepository = mock()
    private val randomUser = getRandomUser()

    @BeforeEach
    internal fun setUp() {
        whenever(mockedUserRepository.checkUserExists(randomUser)).thenReturn(true)
    }

    @Test
    fun `should validate the existence of the user`() {
        val updateUserAction = UpdateUser(mockedUserRepository)

        updateUserAction(randomUser)

        verify(mockedUserRepository, times(1)).checkUserExists(randomUser)
    }

    @Test
    fun `should throw exception if the user is not found`() {
        val updateUserAction = UpdateUser(mockedUserRepository)

        assertThrows<UserNotFoundException> {
            updateUserAction(randomUser.copy(id = getRandomString()))
        }
    }
}