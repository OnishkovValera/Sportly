package itmo.databasemodule.user

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Date

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    var id: Int = 0,

    @field:NotNull
    @field:NotBlank
    @Column(name = "firstname", nullable = false)
    var firstName: String,

    @field:NotNull
    @field:NotBlank
    @Column(name = "lastname", nullable = false)
    var lastName: String,

    @field:NotNull
    @Column(name = "birth_date", nullable = false)
    var birthDate: Date,

    @field:NotNull
    @field:PositiveOrZero
    @Column(name = "balance", nullable = false)
    var balance: Int,

    @field:NotNull
    @field:NotBlank
    @Column(name = "username", nullable = false, unique = true)
    private var username: String,

    @field:NotNull
    @field:NotBlank
    @Column(name = "password", nullable = false)
    private var password: String,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    @ColumnTransformer(write = "?::user_role")
    @Column(name = "role", nullable = false)
    var role: Role
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority(role.name))

    override fun getUsername(): String = username

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', birthDate=$birthDate, balance=$balance, username='$username', role=$role)"
    }
}