package com.memohero.core.domain.gamification

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.user.*

class GamificationService(
    private val levelingAlgorithm: ILevelAlgorithm,
    private val userRepository: UserRepository
) : IGamificationService {
    private val baseExp = 10
    private val baseDamage = 10

    override suspend fun grantExp(user: User, updatedCard: Card): GamificationResult {
        val stat = user.stats.categories[updatedCard.category]!!
        val result = levelingAlgorithm.check(stat.level, stat.exp + baseExp)

        val updatedStat = if (result.didLevelUp) stat.copy(level = stat.level + 1, exp = result.expDifference, needed = result.expNeeded)
            else stat.copy(exp = stat.exp + baseExp)

        user.stats.categories[updatedCard.category] = updatedStat

        user.stats.health += if(user.stats.health == 100) 0 else baseDamage

        userRepository.makePutTransaction(user = user, card = updatedCard)

        return GamificationResult(
            didLevelUp = result.didLevelUp,
            didGetKnockedOut = false,
            category = mapOf(updatedCard.category to updatedStat),
            userStats = user.stats,
        )
    }

    override suspend fun applyDamage(user: User): GamificationResult {
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