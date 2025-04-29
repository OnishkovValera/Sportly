package itmo.databasemodule.controller

import itmo.databasemodule.dto.PlaygroundFullDto
import itmo.databasemodule.dto.PlaygroundMinDto
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.service.LikeService
import itmo.databasemodule.service.PlaygroundService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/playgrounds", produces = [MediaType.APPLICATION_JSON_VALUE])
class PlaygroundController(
    private val service: PlaygroundService,
    private val likeService: LikeService
) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<PlaygroundFullDto> {
        return ResponseEntity.ok().body(service.findById(id))
    }

    @PostMapping("/search")
    fun search(
        @RequestBody(required = false) searchCriteria: SearchCriteriaDto?,
        pageable: Pageable
    ): ResponseEntity<Page<PlaygroundMinDto>> {
        val fields = service.search(searchCriteria, pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", fields.totalElements.toString())
            .body(fields)
    }
}