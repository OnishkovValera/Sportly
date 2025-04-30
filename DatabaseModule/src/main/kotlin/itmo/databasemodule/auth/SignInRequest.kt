package itmo.databasemodule.auth

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SignInRequest(
    @field:NotNull
    @field:Size(min = 4, max = 128, message = "Логин должен содержать от 4 до 128 символов")
    val username: String,

    @field:NotNull
    @field:Size(min = 6, max = 128, message = "Пароль должен содержать от 6 до 128 символов")
    val password: String
)
