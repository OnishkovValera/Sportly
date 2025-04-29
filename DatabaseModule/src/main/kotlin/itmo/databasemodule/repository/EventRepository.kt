package itmo.databasemodule.repository

import itmo.databasemodule.domain.event.Event
import itmo.databasemodule.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Int>, JpaSpecificationExecutor<Event> {
    @Query(value = "SELECT get_participation_count(:eventId)", nativeQuery = true)
    fun getParticipationCount(eventId: Int): Int

    @Query(value = "SELECT get_event_like_count(:eventId)", nativeQuery = true)
    fun getLikeCount(eventId: Int): Int

    @Query(
        value = ("SELECT DISTINCT e FROM Event e " +
                "LEFT JOIN Participation p ON e.id = p.event.id " +
                "WHERE p.user = :user " +
                "ORDER BY e.startsAt DESC"),
    )
    fun findAllByUser(user: User, pageable: Pageable): Page<Event>
}