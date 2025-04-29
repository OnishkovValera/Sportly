package itmo.databasemodule.auth

import itmo.databasemodule.user.UserReadDto

data class AuthResponse(
    val token: String,
    val user: UserReadDto
)