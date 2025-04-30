package itmo.databasemodule.domain.playground

import itmo.databasemodule.domain.location.City
import itmo.databasemodule.domain.location.Station
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType

@Entity
@Table(name = "playgrounds")
data class Playground(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @field:NotNull
    @field:NotBlank
    @Column(name = "name", nullable = false)
    var name: String,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    var city: City,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "playgrounds_stations",
        joinColumns = [JoinColumn(name = "playground_id")],
        inverseJoinColumns = [JoinColumn(name = "station_id")]
    )
    var stations: MutableList<Station> = mutableListOf(),

    @field:NotNull
    @field:NotBlank
    @Column(name = "address", nullable = false)
    var address: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @ColumnTransformer(write = "?::ground_type")
    @Column(name = "ground_type")
    var groundType: GroundType,

    @field:NotNull
    @field:NotBlank
    @Column(name = "game_type", nullable = false)
    var gameType: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @ColumnTransformer(write = "?::surface_type")
    @Column(name = "surface_type")
    var surfaceType: SurfaceType,

    @field:NotNull
    @field:Positive
    @Column(name = "max_players", nullable = false)
    var maxPlayers: Int,

    @field:NotNull
    @field:NotBlank
    @Column(name = "description", nullable = false)
    var description: String,

    @field:NotNull
    @field:Positive
    @Column(name = "length", nullable = false)
    var length: Float,

    @field:NotNull
    @field:Positive
    @Column(name = "width", nullable = false)
    var width: Float,

    @field:Positive
    @Column(name = "height")
    var height: Float?,

    @field:NotNull
    @Column(name = "locker_room", nullable = false)
    var lockerRoom: Boolean,

    @field:NotNull
    @Column(name = "stands", nullable = false)
    var stands: Boolean,

    @field:NotNull
    @Column(name = "shower", nullable = false)
    var shower: Boolean,

    @field:NotNull
    @Column(name = "lighting", nullable = false)
    var lighting: Boolean,

    @field:NotNull
    @Column(name = "parking_space", nullable = false)
    var parkingSpace: Boolean,

    @field:NotNull
    @field:NotBlank
    @Column(name = "additional_features")
    var additionalFeatures: String?,
)
