package com.memohero.core.action.cards

import com.memohero.core.domain.card.Card
import com.memohero.core.domain.card.CardRepository
import com.memohero.core.domain.exceptions.CardAlreadyExistsException

class StoreCard(
    private val cardRepository: CardRepository
) {
    operator fun invoke(card: Card) {
        if (cardRepository.getById(card.id) == null)
            cardRepository.add(card)
        else
            throw CardAlreadyExistsException("Card with id ${ card.id } already exists.")
    }
}