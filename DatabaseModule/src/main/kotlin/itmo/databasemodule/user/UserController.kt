package itmo.databasemodule.user

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService
) {
    @PatchMapping("/me/balance")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    fun updateBalance(@RequestBody balance: BalanceUpdateDto): ResponseEntity<UserReadDto> {
        return ResponseEntity.ok(userService.updateBalance(balance))
    }
}
