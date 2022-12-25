package com.memohero.infrastructure.http.provider

import com.memohero.core.action.cards.GetCards
import com.memohero.core.action.cards.GetVersion
import com.memohero.core.action.cards.StoreCard
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