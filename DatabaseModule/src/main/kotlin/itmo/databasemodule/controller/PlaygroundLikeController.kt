package itmo.databasemodule.controller

import itmo.databasemodule.dto.PlaygroundLikeDto
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
@RequestMapping("/api/v1/playgrounds", produces = [MediaType.APPLICATION_JSON_VALUE])
class PlaygroundLikeController(
    private val likeService: LikeService
) {
    @PostMapping("/{id}/like")
    @PreAuthorize("hasAuthority('USER')")
    fun like(@PathVariable id: Int): ResponseEntity<PlaygroundLikeDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.likePlayground(id))
    }
}