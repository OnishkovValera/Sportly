package itmo.databasemodule.auth

import itmo.databasemodule.exception.ConflictException

class UserAlreadyExistsException(message: String) : ConflictException(message)