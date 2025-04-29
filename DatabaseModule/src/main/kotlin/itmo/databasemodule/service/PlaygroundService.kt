package itmo.databasemodule.service

import itmo.databasemodule.domain.playground.Playground
import itmo.databasemodule.dto.PlaygroundFullDto
import itmo.databasemodule.dto.PlaygroundMinDto
import itmo.databasemodule.dto.toFullDto
import itmo.databasemodule.dto.toMinDto
import itmo.databasemodule.exception.ResourceNotFoundException
import itmo.databasemodule.repository.PlaygroundRepository
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.search.SearchMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaygroundService(
    private val repository: PlaygroundRepository,
    private val searchMapper: SearchMapper<Playground>,
) {
    @Transactional(readOnly = true)
    fun findById(id: Int): PlaygroundFullDto {
        val playground = repository.findById(id).orElseThrow { ResourceNotFoundException("Площадка с id: $id не найдена") }
        val dto = playground.toFullDto()
        dto.avgRating = repository.getAvgRating(id)
        dto.likes = repository.getLikeCount(id)
        return dto
    }

    @Transactional(readOnly = true)
    fun search(criteria: SearchCriteriaDto?, pageable: Pageable): Page<PlaygroundMinDto> {
        val playgrounds = repository.findAll(searchMapper.map(criteria), pageable)
        return playgrounds.map { playground ->
            playground.toMinDto().apply {
                avgRating = repository.getAvgRating(playground.id)
                likes = repository.getLikeCount(playground.id)
            }
        }
    }
}