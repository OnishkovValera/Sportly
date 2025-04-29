package itmo.databasemodule.controller

import itmo.databasemodule.dto.EventLikeDto
import itmo.databasemodule.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/events", produces = [MediaType.APPLICATION_JSON_VALUE])
class EventLikeController(
    private val likeService: LikeService
) {
    @PostMapping("/{id}/like")
    @PreAuthorize("hasAuthority('USER')")
    fun like(@PathVariable id: Int): ResponseEntity<EventLikeDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.likeEvent(id))
    }
}