package itmo.databasemodule.domain.like

import itmo.databasemodule.domain.event.Event
import itmo.databasemodule.user.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime

@Entity
@Table(name = "event_likes")
data class EventLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User?,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    var event: Event,

    @field:NotNull
    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime,
)
