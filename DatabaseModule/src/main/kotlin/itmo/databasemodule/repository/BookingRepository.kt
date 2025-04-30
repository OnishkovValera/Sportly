package itmo.databasemodule.repository

import itmo.databasemodule.domain.booking.Booking
import itmo.databasemodule.domain.session.Session
import itmo.databasemodule.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BookingRepository : CrudRepository<Booking, Int> {
    fun findByUserAndSession(user: User, session: Session): Optional<Booking>
}