package itmo.databasemodule.auth

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.sql.Date

data class SignUpRequest(
    @field:NotNull(message = "Имя пользователя должно присутствовать")
    @field:NotBlank(message = "Имя пользователя не должно быть пустым")
    val firstName: String,

    @field:NotNull(message = "Фамилия пользователя должна присутствовать")
    @field:NotBlank(message = "Фамилия пользователя не должна быть пустой")
    val lastName: String,

    @field:NotNull(message = "Дата рождения пользователя должна присутствовать")
    val birthDate: Date,

    @field:NotNull
    @field:Size(min = 4, max = 128, message = "Логин должен содержать от 4 до 128 символов")
    val username: String,

    @field:NotNull
    @field:Size(min = 6, max = 128, message = "Пароль должен содержать от 6 до 128 символов")
    val password: String
)