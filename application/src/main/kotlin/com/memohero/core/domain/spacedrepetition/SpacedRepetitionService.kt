package com.memohero.core.domain.spacedrepetition

import com.memohero.core.domain.card.CardStudyMetadata
import com.memohero.core.domain.spacedrepetition.supermemo2.Supermemo2

class SpacedRepetitionService: ISpacedRepetitionService {

    override fun calculateInterval(cardMetadata: CardStudyMetadata, quality: Int) = Supermemo2.calculate(
        cardMetadata.interval,
        cardMetadata.repetition,
        cardMetadata.easeFactor,
        quality,
    )
}