package itmo.databasemodule.service

import itmo.databasemodule.domain.event.EventStatus
import itmo.databasemodule.domain.participation.Participation
import itmo.databasemodule.dto.ParticipationDto
import itmo.databasemodule.dto.toDto
import itmo.databasemodule.exception.BadRequestException
import itmo.databasemodule.exception.ConflictException
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.EventRepository
import itmo.databasemodule.repository.ParticipationRepository
import itmo.databasemodule.user.User
import itmo.databasemodule.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class ParticipationService(
    private val repository: ParticipationRepository,
    private val eventRepository: EventRepository,
    private val userService: UserService
) {
    @Transactional
    fun participate(eventId: Int): ParticipationDto {
        val event = eventRepository.findById(eventId)
            .orElseThrow { ResourceNotFoundException("Событие с id: $eventId не найдено") }

        val currentUser: User = userService.getCurrentUser()

        if (repository.existsByUserIdAndEventId(currentUser.id, eventId)) {
            throw ConflictException("Пользователь с id: ${currentUser.id} уже записан на событие с id: ${eventId}")
        }
        val participation = Participation(
            user = currentUser,
            event = event,
            createdAt = ZonedDateTime.now(),
        )

        repository.save(participation)
        return participation.toDto()
    }

    @Transactional
    fun cancel(eventId: Int): ParticipationDto {
        val event = eventRepository.findById(eventId)
            .orElseThrow { ResourceNotFoundException("Событие с id: $eventId не найдено") }
        val currentUser = userService.getCurrentUser()
        val participation = repository.findByUserAndEvent(currentUser, event)
            .orElseThrow { ResourceNotFoundException("Пользователь с id: ${currentUser.id} не записан на событие с id: ${event.id}") }
        if (event.status == EventStatus.FINISHED) {
            throw BadRequestException("Нельзя отписаться от события в прошлом")
        }
        repository.delete(participation)
        return participation.toDto()
    }
}