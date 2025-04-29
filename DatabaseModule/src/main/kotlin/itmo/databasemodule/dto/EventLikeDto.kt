package itmo.databasemodule.dto

import itmo.databasemodule.domain.like.EventLike
import itmo.databasemodule.user.UserReadDto
import itmo.databasemodule.user.toDto
import java.time.ZonedDateTime

data class EventLikeDto(
    val id: Int,
    val user: UserReadDto?,
    val eventId: Int,
    val eventTitle: String,
    val createdAt: ZonedDateTime
)

fun EventLike.toDto() = EventLikeDto(
    this.id,
    this.user?.toDto(),
    this.event.id,
    this.event.title,
    this.createdAt
)