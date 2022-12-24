package com.memohero.infrastructure.http.provider

import com.memohero.core.action.GetCards
import com.memohero.core.action.GetVersion
import com.memohero.core.action.StoreCard
import com.memohero.infrastructure.Repositories

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
}