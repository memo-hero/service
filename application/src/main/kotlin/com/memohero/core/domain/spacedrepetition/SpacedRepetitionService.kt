package com.memohero.core.domain.spacedrepetition

import com.memohero.core.domain.card.Card

class SpacedRepetitionService: ISpacedRepetitionService {

    override fun calculateInterval(card: Card, quality: Int): Int {
        return -1
    }
}

interface ISpacedRepetitionService {
    fun calculateInterval(card: Card, quality: Int): Int
}