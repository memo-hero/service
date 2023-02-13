package com.memohero.core.domain.gamification

import com.memohero.core.domain.user.*

class GamificationService(
    private val levelingAlgorithm: ILevelAlgorithm,
    private val userRepository: UserRepository
) : IGamificationService {
    private val baseExp = 10
    private val baseDamage = 10

    override fun grantExp(user: User, category: Category): GamificationResult {
        val stat = user.stats.categories[category]!!
        val result = levelingAlgorithm.check(stat.level, stat.exp + baseExp)

        val updatedStat = if (result.didLevelUp) stat.copy(level = stat.level + 1, exp = result.expDifference, needed = result.expNeeded)
            else stat.copy(exp = stat.exp + baseExp)

        user.stats.categories[category] = updatedStat

        userRepository.updateUser(user)

        return GamificationResult(
            didLevelUp = result.didLevelUp,
            didGetKnockedOut = false,
            category = mapOf(category to updatedStat),
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