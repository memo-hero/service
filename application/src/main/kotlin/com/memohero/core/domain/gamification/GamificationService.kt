package com.memohero.core.domain.gamification

import com.memohero.core.domain.user.*

class GamificationService(
    private val levelingAlgorithm: ILevelAlgorithm,
    private val userRepository: UserRepository
) : IGamificationService {
    private val baseExp = 10
    private val baseDamage = 10

    override fun grantExp(user: User, category: Category): GamificationResult {
        val stat = user.stats.categories.first { it.category == category }
        val result = levelingAlgorithm.check(stat.level, stat.exp + baseExp)

        val updatedStat = if (result.didLevelUp) stat.copy(level = stat.level + 1, exp = result.expDifference)
            else stat.copy(exp = stat.exp + baseExp)

        val mutableList = user.stats.categories.toMutableList()
        mutableList[user.stats.categories.indexOf(stat)] = updatedStat

        userRepository.updateUser(user.copy(
            stats = user.stats.copy(
                categories = mutableList
            )
        ))

        return GamificationResult(
            didLevelUp = result.didLevelUp,
            didGetKnockedOut = false,
            category = updatedStat,
            userStats = user.stats,
        )
    }

    override fun applyDamage(user: User): GamificationResult {
        val newHealth = if (user.stats.health <= baseDamage) 0
        else user.stats.health - 10
        val updatedUser = user.copy(stats = user.stats.copy(health = newHealth))

        userRepository.updateUser(updatedUser)

        return GamificationResult(
            didGetKnockedOut = newHealth == 0,
            userStats = updatedUser.stats
        )
    }
}