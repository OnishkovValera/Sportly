package itmo.databasemodule.dto

import itmo.databasemodule.domain.participation.Participation
import itmo.databasemodule.user.UserReadDto
import itmo.databasemodule.user.toDto
import java.time.ZonedDateTime

data class ParticipationDto(
    val id: Int,
    val user: UserReadDto,
    val eventId: Int,
    val eventTitle: String,
    val createdAt: ZonedDateTime
)

fun Participation.toDto() = ParticipationDto(
    this.id,
    this.user.toDto(),
    this.event.id,
    this.event.title,
    this.createdAt
)
