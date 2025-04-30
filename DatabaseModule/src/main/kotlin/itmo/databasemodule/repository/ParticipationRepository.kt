package itmo.databasemodule.repository

import itmo.databasemodule.domain.event.Event
import itmo.databasemodule.domain.participation.Participation
import itmo.databasemodule.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ParticipationRepository : JpaRepository<Participation, Int> {
    fun findByUserAndEvent(user: User, event: Event): Optional<Participation>
    fun existsByUserIdAndEventId(userId: Int, eventId: Int): Boolean
}