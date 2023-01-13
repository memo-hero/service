package com.memohero.core.domain.gamification

import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.CategoryStats
import com.memohero.core.domain.user.Stats
import com.memohero.core.domain.user.UserRepository
import com.memohero.tools.mothers.getRandomCategoryStat
import com.memohero.tools.mothers.getRandomStats
import com.memohero.tools.mothers.getRandomUser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock

class GamificationServiceTest {
    private val mockedLevelingAlgorithm: ILevelAlgorithm = LevelAlgorithm()
    private val mockedUserRepository: UserRepository = mock()
    private val gamificationService = GamificationService(mockedLevelingAlgorithm, mockedUserRepository)

    @ParameterizedTest
    @MethodSource("levelAndExp")
    fun `should properly assign exp and leveling`(currentLevel: Int, currentExp: Int, gamificationResult: GamificationResult) {
        val studiedCategory = Category.ARTS
        val user = getUserWithSpecifiedExp(category = studiedCategory, exp = currentExp, level = currentLevel)

        val result = gamificationService.grantExp(user = user, category = studiedCategory)

        assertThat(result.didLevelUp).isEqualTo(gamificationResult.didLevelUp)
        assertThat(result.didGetKnockedOut).isEqualTo(gamificationResult.didGetKnockedOut)
    }

    @ParameterizedTest
    @MethodSource("healthTests")
    fun `should update user's health when answering wrong`(currentHealth: Int, newHealth: Int, didGetKnockedOut: Boolean) {
        val user = getRandomUser(stats = getRandomStats(health = currentHealth))

        val result = gamificationService.applyDamage(user)

        assertThat(result.didGetKnockedOut).isEqualTo(didGetKnockedOut)
    }

    companion object {
        @JvmStatic
        fun levelAndExp() = listOf(
            // Result for category at 0 exp
            Arguments.of(
                1,
                0,
                getGamificationResult(
                    category = getRandomCategoryStat(category = Category.ARTS, level = 1, exp = 10)
                ),
            ),

            // Result for category with not enough exp to level up
            Arguments.of(
                2,
                30,
                getGamificationResult(
                    category = getRandomCategoryStat(category = Category.ARTS, level = 2, exp = 40)
                ),
            ),

            // Result for category with just enough exp to level up
            Arguments.of(
                1,
                65,
                getGamificationResult(
                    didLevelUp = true,
                    category = getRandomCategoryStat(category = Category.ARTS, level = 2, exp = 0)
                ),
            ),

            // Result for category with extra exp after leveling up
            Arguments.of(
                1,
                70,
                getGamificationResult(
                    didLevelUp = true,
                    category = getRandomCategoryStat(category = Category.ARTS, level = 2, exp = 5)
                ),
            ),
        )

        @JvmStatic
        fun healthTests() = listOf(
            // current health | new health | did get knocked up
            Arguments.of(100, 90, false),
            Arguments.of(10, 0, true),
            Arguments.of(5, 0, true),
        )
    }

    private fun getUserWithSpecifiedExp(
        category: Category = Category.values().random(),
        level: Int = 1,
        exp: Int = 0
    ) = getRandomUser(
        stats = getRandomStats(
            categories = listOf(
                getRandomCategoryStat(
                    category = category,
                    level = level,
                    exp = exp,
                )
            )
        )
    )
}

fun getGamificationResult(
    didLevelUp: Boolean = false,
    didGetKnockedOut: Boolean = false,
    category: CategoryStats = getRandomCategoryStat(),
    userStats: Stats = getRandomStats(),
) = GamificationResult(
    didLevelUp = didLevelUp,
    didGetKnockedOut = didGetKnockedOut,
    category = category,
    userStats = userStats,
)