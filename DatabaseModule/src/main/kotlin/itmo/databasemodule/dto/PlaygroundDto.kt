package itmo.databasemodule.dto

import itmo.databasemodule.domain.playground.GroundType
import itmo.databasemodule.domain.playground.Playground
import itmo.databasemodule.domain.playground.SurfaceType

data class PlaygroundMinDto(
    val id: Int,
    val name: String,
    val description: String,
    val stations: List<StationDto>,
    val address: String,
    val gameType: String,
    val groundType: GroundType,
    val surfaceType: SurfaceType,
    var avgRating: Double? = null,
    var likes: Int = 0
)

data class PlaygroundFullDto(
    val id: Int,
    val name: String,
    val description: String,
    val city: CityDto,
    val stations: List<StationDto>,
    val address: String,
    val gameType: String,
    val groundType: GroundType,
    val surfaceType: SurfaceType,
    val maxPlayers: Int,
    val length: Float,
    val width: Float,
    val height: Float?,
    val lockerRoom: Boolean,
    val stands: Boolean,
    val shower: Boolean,
    val lighting: Boolean,
    val parkingSpace: Boolean,
    val additionalFeatures: String?,
    var avgRating: Double? = null,
    var likes: Int = 0
)

fun Playground.toMinDto() = PlaygroundMinDto(
    this.id,
    this.name,
    this.description,
    this.stations.map { it.toDto() },
    this.address,
    this.gameType,
    this.groundType,
    this.surfaceType
)

fun Playground.toFullDto() = PlaygroundFullDto(
    this.id,
    this.name,
    this.description,
    this.city.toDto(),
    this.stations.map { it.toDto() },
    this.address,
    this.gameType,
    this.groundType,
    this.surfaceType,
    this.maxPlayers,
    this.length,
    this.width,
    this.height,
    this.lockerRoom,
    this.stands,
    this.shower,
    this.lighting,
    this.parkingSpace,
    this.additionalFeatures
)
