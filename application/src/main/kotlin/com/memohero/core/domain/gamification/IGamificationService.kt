package com.memohero.core.domain.gamification

import com.memohero.core.domain.user.Category
import com.memohero.core.domain.user.User

interface IGamificationService {
    fun grantExp(user: User, category: Category): GamificationResult
    fun applyDamage(user: User): GamificationResult
}