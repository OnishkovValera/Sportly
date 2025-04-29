package itmo.databasemodule.service

import itmo.databasemodule.domain.feedback.Feedback
import itmo.databasemodule.dto.FeedbackCreateDto
import itmo.databasemodule.dto.FeedbackReadDto
import itmo.databasemodule.dto.SessionDto
import itmo.databasemodule.dto.toDto
import itmo.databasemodule.exception.ConflictException
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.FeedbackRepository
import itmo.databasemodule.repository.PlaygroundRepository
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.search.SearchMapper
import itmo.databasemodule.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime

@Service
class FeedbackService(
    private val repository: FeedbackRepository,
    private val userService: UserService,
    private val searchMapper: SearchMapper<Feedback>,
    private val playgroundRepository: PlaygroundRepository
) {
    @Transactional(readOnly = true)
    fun search(criteria: SearchCriteriaDto?, pageable: Pageable): Page<FeedbackReadDto> =
        repository.findAll(searchMapper.map(criteria), pageable).map { it.toDto() }

    @Transactional(readOnly = true)
    fun findAllByPlayground(playgroundId: Int, pageable: Pageable): Page<FeedbackReadDto> {
        return repository.findAllByPlaygroundIdOrderByCreatedAtDesc(playgroundId, pageable).map { it.toDto() }
    }

    @Transactional
    fun create(createDto: FeedbackCreateDto): FeedbackReadDto {
        val user = userService.getCurrentUser()
        if (repository.existsByUserIdAndPlaygroundId(user.id, createDto.playgroundId)) {
            throw ConflictException("Пользователь с id: ${user.id} уже оставлял отзыв к площадке с id: ${createDto.playgroundId}")
        }
        val feedback = Feedback(user = user,
            message = createDto.message,
            rating = createDto.rating,
            createdAt = ZonedDateTime.now(),
            playground = playgroundRepository.findById(createDto.playgroundId)
                .orElseThrow { ResourceNotFoundException("Площадка с id: ${createDto.playgroundId} не найдена") })
        repository.save(feedback)
        return feedback.toDto()
    }
}