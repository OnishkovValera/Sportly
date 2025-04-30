package itmo.databasemodule.dto

import itmo.databasemodule.domain.location.City

data class CityDto(
    val id: Int,
    val name: String
)

fun City.toDto() = CityDto(
    this.id,
    this.name
)
