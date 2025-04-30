package itmo.databasemodule.dto

import itmo.databasemodule.domain.event.Event
import itmo.databasemodule.domain.event.EventStatus
import java.time.ZonedDateTime

data class EventDto(
    val id: Int,
    val title: String,
    val city: CityDto,
    val stations: List<StationDto>,
    val status: EventStatus,
    val address: String,
    val description: String,
    val startsAt: ZonedDateTime,
    val maxParticipants: Int,
    var likes: Int = 0,
    var participants: Int = 0
)

fun Event.toDto() = EventDto(
    this.id,
    this.title,
    this.city.toDto(),
    this.stations.map { it.toDto() },
    this.status,
    this.address,
    this.description,
    this.startsAt,
    this.maxParticipants
)
