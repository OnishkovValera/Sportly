package itmo.databasemodule.controller

import itmo.databasemodule.dto.BookingDto
import itmo.databasemodule.service.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sessions", produces = [MediaType.APPLICATION_JSON_VALUE])
class BookingController(
    private val service: BookingService
) {
    @PostMapping("/{id}/book")
    @PreAuthorize("hasAuthority('USER')")
    fun book(@PathVariable id: Int): ResponseEntity<BookingDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.book(id))
    }
}