package com.memohero.core.domain.gamification

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.User

interface IGamificationService {
    suspend fun grantExp(user: User, updatedCard: Card): GamificationResult
    suspend fun applyDamage(user: User): GamificationResult
}