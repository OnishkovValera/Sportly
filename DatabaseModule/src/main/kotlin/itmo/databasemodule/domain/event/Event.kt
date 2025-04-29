package itmo.databasemodule.domain.event

import itmo.databasemodule.domain.location.City
import itmo.databasemodule.domain.location.Station
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.time.ZonedDateTime

@Entity
@Table(name = "events")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @field:NotNull
    @field:NotBlank
    @Column(name = "title", nullable = false)
    var title: String,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    var city: City,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "events_stations",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "station_id")]
    )
    var stations: MutableList<Station> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @ColumnTransformer(write = "?::event_status")
    @Column(name = "status", nullable = false)
    var status: EventStatus,

    @field:NotNull
    @field:NotBlank
    @Column(name = "address", nullable = false)
    var address: String,

    @field:NotNull
    @field:NotBlank
    @Column(name = "description", nullable = false)
    var description: String,

    @field:NotNull
    @Column(name = "starts_at", nullable = false)
    var startsAt: ZonedDateTime,

    @field:NotNull
    @field:PositiveOrZero
    @Column(name = "max_participants", nullable = false)
    var maxParticipants: Int,
)
