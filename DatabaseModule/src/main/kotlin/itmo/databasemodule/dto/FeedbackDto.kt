package itmo.databasemodule.dto

import itmo.databasemodule.domain.feedback.Feedback
import itmo.databasemodule.user.UserReadDto
import itmo.databasemodule.user.toDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime

data class FeedbackReadDto(
    val id: Int,
    val user: UserReadDto?,
    val playgroundId: Int,
    val playgroundName: String,
    val rating: Int,
    val message: String,
    val createdAt: ZonedDateTime
)

data class FeedbackCreateDto(
    @NotNull(message = "id площадки должно присутствовать")
    val playgroundId: Int,
    @NotNull(message = "Сообщение отзыва должно присутствовать")
    @NotBlank(message = "Сообщение отзыва не должно быть пустым")
    val message: String,
    @NotNull(message = "Рейтинг отзыва должен присутствовать")
    val rating: Int
)

fun Feedback.toDto() = FeedbackReadDto(
    this.id,
    this.user?.toDto(),
    this.playground.id,
    this.playground.name,
    this.rating,
    this.message,
    this.createdAt
)
