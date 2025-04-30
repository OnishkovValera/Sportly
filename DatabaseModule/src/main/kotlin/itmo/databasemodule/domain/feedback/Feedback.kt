package itmo.databasemodule.domain.feedback

import itmo.databasemodule.domain.playground.Playground
import itmo.databasemodule.user.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.ZonedDateTime

@Entity
@Table(name = "feedbacks")
class Feedback(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User?,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playground_id", nullable = false)
    var playground: Playground,

    @field:NotNull
    @Column(name = "rating", nullable = false)
    var rating: Int,

    @field:NotNull
    @Column(name = "message", nullable = false)
    var message: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime
)