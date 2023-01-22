package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.*
import com.memohero.core.action.system.GetVersion
import com.memohero.core.action.users.CreateUser
import com.memohero.core.action.users.GetUserByID
import com.memohero.core.action.users.UpdateUser
import com.memohero.infrastructure.Repositories
import com.memohero.infrastructure.Services

object Actions {

    val getVersion by lazy {
        GetVersion()
    }

    val storeCard by lazy {
        StoreCard(Repositories.cardRepository)
    }

    val getCards by lazy {
        GetCards(Repositories.cardRepository)
    }

    val studyCard by lazy {
        StudyCard(
            Repositories.cardRepository,
            Repositories.userRepository,
            Services.spacedRepetitionService,
            Services.gamificationService,
        )
    }

    val getCardById by lazy {
        GetCardById(Repositories.cardRepository)
    }

    val getCardsByTag by lazy {
        GetCardsByTag(Repositories.cardRepository)
    }

    val createUser by lazy {
        CreateUser(Repositories.userRepository)
    }

    val getUserByID by lazy {
        GetUserByID(Repositories.userRepository)
    }

    val updateUser by lazy {
        UpdateUser(Repositories.userRepository)
    }
}