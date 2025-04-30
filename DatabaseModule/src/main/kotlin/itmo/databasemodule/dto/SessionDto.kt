package itmo.databasemodule.dto

import itmo.databasemodule.domain.session.Session
import itmo.databasemodule.domain.session.SessionStatus
import java.time.ZonedDateTime

data class SessionDto(
    val id: Int,
    val playgroundId: Int,
    val playgroundName: String,
    val bookingPrice: Int,
    val status: SessionStatus,
    val startsAt: ZonedDateTime,
)

fun Session.toDto() = SessionDto(
    this.id,
    this.playgrund.id,
    this.playgrund.name,
    this.bookingPrice,
    this.status,
    this.startsAt
)