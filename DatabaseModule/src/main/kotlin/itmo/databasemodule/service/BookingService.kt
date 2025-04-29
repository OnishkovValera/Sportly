package itmo.databasemodule.service

import itmo.databasemodule.domain.booking.Booking
import itmo.databasemodule.dto.BookingDto
import itmo.databasemodule.dto.toDto
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.BookingRepository
import itmo.databasemodule.repository.SessionRepository
import itmo.databasemodule.user.User
import itmo.databasemodule.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val sessionRepository: SessionRepository,
    private val userService: UserService
) {
    @Transactional
    fun book(sessionId: Int): BookingDto{
        val session = sessionRepository.findById(sessionId)
            .orElseThrow { ResourceNotFoundException("Сессия с id: $sessionId не найдена") }

        val currentUser: User = userService.getCurrentUser()
        val booking = Booking(
            user = currentUser,
            session = session,
            createdAt = ZonedDateTime.now(),
        )

        bookingRepository.save(booking)
        booking.user.balance = userService.getBalanceByUser(currentUser)
        return booking.toDto()
    }
}