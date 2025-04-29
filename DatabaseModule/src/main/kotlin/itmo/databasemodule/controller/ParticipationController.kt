package itmo.databasemodule.controller

import itmo.databasemodule.dto.ParticipationDto
import itmo.databasemodule.service.ParticipationService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/events", produces = [MediaType.APPLICATION_JSON_VALUE])
class ParticipationController(
    private val service: ParticipationService
) {
    @PostMapping("/{id}/participate")
    @PreAuthorize("hasAuthority('USER')")
    fun book(@PathVariable id: Int): ResponseEntity<ParticipationDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.participate(id))
    }

    @DeleteMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('USER')")
    fun cancel(@PathVariable id: Int): ResponseEntity<ParticipationDto> {
        return ResponseEntity.status(HttpStatus.OK).body(service.cancel(id))
    }
}