package itmo.databasemodule.controller

import itmo.databasemodule.dto.FeedbackCreateDto
import itmo.databasemodule.dto.FeedbackReadDto
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.service.FeedbackService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/feedbacks", produces = [MediaType.APPLICATION_JSON_VALUE])
class FeedbackController(
    private val service: FeedbackService
) {
    @GetMapping
    fun findAllByPlayground(
        @RequestParam playgroundId: Int,
        pageable: Pageable
    ): ResponseEntity<Page<FeedbackReadDto>> {
        val feedbacks = service.findAllByPlayground(playgroundId, pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", feedbacks.totalElements.toString())
            .body(feedbacks)
    }

    @PostMapping("/search")
    fun search(
        @RequestBody(required = false) searchCriteria: SearchCriteriaDto?,
        pageable: Pageable
    ): ResponseEntity<Page<FeedbackReadDto>> {
        val feedbacks = service.search(searchCriteria, pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", feedbacks.totalElements.toString())
            .body(feedbacks)
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    fun create(@RequestBody createDto: FeedbackCreateDto): ResponseEntity<FeedbackReadDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createDto))
    }
}