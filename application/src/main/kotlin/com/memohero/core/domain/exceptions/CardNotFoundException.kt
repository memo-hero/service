package com.memohero.core.domain.exceptions

import java.util.*

class CardNotFoundException(cardId: UUID) : Throwable("Card with id $cardId was not found.")