package com.memohero.core.domain.gamification

import com.memohero.core.domain.user.CategoryStats
import com.memohero.core.domain.user.Stats

data class GamificationResult(
    val didLevelUp: Boolean = false,
    val didGetKnockedOut: Boolean = false,
    val category: CategoryStats? = null, // En caso de lvl up, se envía actualizado
    val userStats: Stats? = null, // En caso de perder, se envía los stats actualizados
)