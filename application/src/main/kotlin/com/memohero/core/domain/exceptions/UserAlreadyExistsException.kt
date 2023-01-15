package com.memohero.core.domain.exceptions

class UserAlreadyExistsException(id: String) : Throwable("User with id $id already exists.")