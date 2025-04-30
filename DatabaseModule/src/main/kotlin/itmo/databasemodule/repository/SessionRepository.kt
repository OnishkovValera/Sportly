package itmo.databasemodule.repository

import itmo.databasemodule.domain.session.Session
import itmo.databasemodule.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Int>, JpaSpecificationExecutor<Session> {
    @Query(
        value = ("SELECT DISTINCT s FROM Session s " +
                "LEFT JOIN Booking b ON s.id = b.session.id " +
                "WHERE b.user = :user " +
                "ORDER BY s.startsAt DESC"),
    )
    fun findAllByUser(user: User, pageable: Pageable): Page<Session>
}