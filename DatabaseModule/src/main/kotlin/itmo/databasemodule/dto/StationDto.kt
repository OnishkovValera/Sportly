package itmo.databasemodule.dto

import itmo.databasemodule.domain.location.Station

data class StationDto(
    val id: Int,
    val name: String,
    val city: CityDto
)

fun Station.toDto() = StationDto(
    this.id,
    this.name,
    this.city.toDto()
)
