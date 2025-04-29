package itmo.databasemodule.service

import itmo.databasemodule.domain.event.Event
import itmo.databasemodule.dto.*
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.EventRepository
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.search.SearchMapper
import itmo.databasemodule.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventService(
    private val repository: EventRepository,
    private val searchMapper: SearchMapper<Event>,
    private val userService: UserService
) {
    @Transactional(readOnly = true)
    fun findAllMy(pageable: Pageable): Page<EventDto> =
        repository.findAllByUser(userService.getCurrentUser(), pageable).map { it.toDto() }

    @Transactional(readOnly = true)
    fun findById(id: Int): EventDto {
        val event = repository.findById(id).orElseThrow { ResourceNotFoundException("Событие с id: $id не найдено") }
        val dto = event.toDto()
        dto.likes = repository.getLikeCount(id)
        return dto
    }

    @Transactional(readOnly = true)
    fun search(criteria: SearchCriteriaDto?, pageable: Pageable): Page<EventDto> {
        val events = repository.findAll(searchMapper.map(criteria), pageable)
        return events.map { event ->
            event.toDto().apply {
                likes = repository.getLikeCount(event.id)
                participants = repository.getParticipationCount(event.id)
            }
        }
    }
}