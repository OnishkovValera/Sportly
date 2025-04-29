package itmo.databasemodule.dto

import itmo.databasemodule.domain.booking.Booking
import itmo.databasemodule.user.UserReadDto
import itmo.databasemodule.user.toDto
import java.time.ZonedDateTime

data class BookingDto(
    val id: Int,
    val user: UserReadDto,
    val session: SessionDto,
    val createdAt: ZonedDateTime,
)

fun Booking.toDto() = BookingDto(
    this.id,
    this.user.toDto(),
    this.session.toDto(),
    this.createdAt
)
