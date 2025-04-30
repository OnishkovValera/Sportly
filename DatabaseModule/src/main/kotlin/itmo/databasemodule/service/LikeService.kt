package itmo.databasemodule.service

import itmo.databasemodule.domain.like.EventLike
import itmo.databasemodule.domain.like.PlaygroundLike
import itmo.databasemodule.dto.EventLikeDto
import itmo.databasemodule.dto.PlaygroundLikeDto
import itmo.databasemodule.dto.toDto
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.EventLikeRepository
import itmo.databasemodule.repository.EventRepository
import itmo.databasemodule.repository.PlaygroundLikeRepository
import itmo.databasemodule.repository.PlaygroundRepository
import itmo.databasemodule.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class LikeService(
    private val playgroundLikeRepository: PlaygroundLikeRepository,
    private val playgroundRepository: PlaygroundRepository,
    private val eventLikeRepository: EventLikeRepository,
    private val eventRepository: EventRepository,
    private val userService: UserService
) {
    @Transactional
    fun likePlayground(playgroundId: Int): PlaygroundLikeDto {
        val playground =
            playgroundRepository.findById(playgroundId)
                .orElseThrow { ResourceNotFoundException("Площадка с id: $playgroundId не найдена") }
        val user = userService.getCurrentUser()
        val like = PlaygroundLike(
            user = user,
            playground = playground,
            createdAt = ZonedDateTime.now()
        )
        playgroundLikeRepository.save(like)
        return like.toDto()
    }

    @Transactional
    fun likeEvent(eventId: Int): EventLikeDto {
        val event =
            eventRepository.findById(eventId)
                .orElseThrow { ResourceNotFoundException("Событие с id: $eventId не найдено") }
        val user = userService.getCurrentUser()
        val like = EventLike(
            user = user,
            event = event,
            createdAt = ZonedDateTime.now()
        )
        eventLikeRepository.save(like)
        return like.toDto()
    }
}