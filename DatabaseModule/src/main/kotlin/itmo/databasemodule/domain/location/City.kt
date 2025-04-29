package itmo.databasemodule.domain.location

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "cities")
data class City(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @field:NotNull
    @field:NotBlank
    @Column(name = "name", nullable = false)
    var name: String
)
