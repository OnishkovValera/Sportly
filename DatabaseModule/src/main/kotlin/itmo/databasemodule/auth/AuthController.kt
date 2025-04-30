package itmo.databasemodule.auth

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpRequest: SignUpRequest): ResponseEntity<AuthResponse> {
        val response = authService.signUp(signUpRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody @Valid signInRequest: SignInRequest): ResponseEntity<AuthResponse> {
        val response = authService.signIn(signInRequest)
        return ResponseEntity.ok(response)
    }
}
