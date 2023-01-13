package com.memohero.core.domain.spacedrepetition

import com.memohero.core.domain.card.CardStudyMetadata

interface ISpacedRepetitionService {
    fun calculateInterval(cardMetadata: CardStudyMetadata, quality: Int): CardStudyMetadata
}