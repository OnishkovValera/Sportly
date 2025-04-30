package itmo.databasemodule.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>

    override fun findById(id: Int): Optional<User>

    fun existsByUsername(username: String): Boolean

    @Query("select u.balance from User u where u = :user")
    fun getBalanceByUser(user: User): Int
}
