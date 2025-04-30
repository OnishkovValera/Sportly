package itmo.databasemodule.controller

import itmo.databasemodule.dto.EventDto
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.service.EventService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/events", produces = [MediaType.APPLICATION_JSON_VALUE])
class EventController(
    private val service: EventService
) {
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    fun findAllMy(pageable: Pageable): ResponseEntity<Page<EventDto>> {
        val sessions = service.findAllMy(pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", sessions.totalElements.toString())
            .body(sessions)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<EventDto> {
        return ResponseEntity.ok().body(service.findById(id))
    }

    @PostMapping("/search")
    fun search(
        @RequestBody(required = false) searchCriteria: SearchCriteriaDto?,
        pageable: Pageable
    ): ResponseEntity<Page<EventDto>> {
        val events = service.search(searchCriteria, pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", events.totalElements.toString())
            .body(events)
    }
}