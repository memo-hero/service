package com.memohero.core.domain.spacedrepetition

import com.memohero.core.domain.card.CardStudyMetadata
import com.memohero.core.domain.spacedrepetition.supermemo2.Supermemo2Result

interface ISpacedRepetitionService {
    fun calculateInterval(cardMetadata: CardStudyMetadata, quality: Int): Supermemo2Result
}