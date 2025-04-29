package itmo.databasemodule.controller

import itmo.databasemodule.dto.SessionDto
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.service.SessionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/sessions", produces = [MediaType.APPLICATION_JSON_VALUE])
class SessionController(
    private val service: SessionService
) {
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    fun findAllMy(pageable: Pageable): ResponseEntity<Page<SessionDto>> {
        val sessions = service.findAllMy(pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", sessions.totalElements.toString())
            .body(sessions)
    }

    @PostMapping("/search")
    fun search(
        @RequestBody(required = false) searchCriteria: SearchCriteriaDto?,
        pageable: Pageable
    ): ResponseEntity<Page<SessionDto>> {
        val sessions = service.search(searchCriteria, pageable)
        return ResponseEntity.ok()
            .header("X-Total-Count", sessions.totalElements.toString())
            .body(sessions)
    }
}