package itmo.databasemodule.user

import java.sql.Date

data class UserReadDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val balance: Int?,
    val role: Role,
    val birthDate: Date
)

fun User.toDto(): UserReadDto {
    return UserReadDto(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        balance = this.balance,
        role = this.role,
        username = this.username,
        birthDate = this.birthDate
    )
}
