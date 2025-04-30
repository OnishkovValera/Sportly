package itmo.databasemodule.auth

import itmo.databasemodule.security.JwtService
import itmo.databasemodule.user.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService
) {
    @Transactional
    fun signUp(signUpRequest: SignUpRequest): AuthResponse {
        if (userRepository.existsByUsername(signUpRequest.username)) {
            throw UserAlreadyExistsException(
                "Пользователь с логином ${signUpRequest.username} уже существует"
            )
        }

        val user = User(
            username = signUpRequest.username,
            password = passwordEncoder.encode(signUpRequest.password),
            firstName = signUpRequest.firstName,
            lastName = signUpRequest.lastName,
            birthDate = signUpRequest.birthDate,
            balance = 0,
            role = Role.USER
        )

        userRepository.save(user)
        val token = jwtService.generateToken(user)
        return AuthResponse(token, user.toDto())
    }

    @Transactional
    fun signIn(signInRequest: SignInRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInRequest.username,
                signInRequest.password
            )
        )
        val userDetails = userDetailsService.loadUserByUsername(signInRequest.username)
        val token = jwtService.generateToken(userDetails)
        val user = userService.getByUsername(signInRequest.username)
        return AuthResponse(token, user.toDto())
    }
}