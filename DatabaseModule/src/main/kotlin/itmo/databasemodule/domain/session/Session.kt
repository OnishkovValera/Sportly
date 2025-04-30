package itmo.databasemodule.domain.session

import itmo.databasemodule.domain.playground.Playground
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.time.ZonedDateTime

@Entity
@Table(name = "sessions")
class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @field:NotNull
    @Column(name = "starts_at", nullable = false)
    var startsAt: ZonedDateTime,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @ColumnTransformer(write = "?::session_status")
    @Column(name = "status", nullable = false)
    var status: SessionStatus,

    @field:NotNull
    @field:Positive
    @Column(name = "booking_price", nullable = false)
    var bookingPrice: Int,

    @field:NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playground_id", nullable = false)
    var playgrund: Playground
)
