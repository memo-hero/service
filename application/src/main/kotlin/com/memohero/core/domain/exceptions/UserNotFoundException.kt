package com.memohero.core.domain.exceptions

class UserNotFoundException(id: String) : Throwable("User with id $id was not found.")