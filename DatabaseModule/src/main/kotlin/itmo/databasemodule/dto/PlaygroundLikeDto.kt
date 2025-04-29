package itmo.databasemodule.dto

import itmo.databasemodule.domain.like.PlaygroundLike
import itmo.databasemodule.user.UserReadDto
import itmo.databasemodule.user.toDto
import java.time.ZonedDateTime

data class PlaygroundLikeDto(
    val id: Int,
    val user: UserReadDto?,
    val playgroundId: Int,
    val playgroundName: String,
    val createdAt: ZonedDateTime,
)

fun PlaygroundLike.toDto() = PlaygroundLikeDto(
    this.id,
    this.user?.toDto(),
    this.playground.id,
    this.playground.name,
    this.createdAt,
)
