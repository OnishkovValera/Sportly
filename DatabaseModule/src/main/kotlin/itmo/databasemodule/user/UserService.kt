package itmo.databasemodule.user

import itmo.databasemodule.exception.BadRequestException
import itmo.databasemodule.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getByUsername(username: String): User {
        return userRepository.findByUsername(username)
            .orElseThrow {
                UserNotFoundException("Пользователь с логином: $username не найден")
            }
    }

    fun updateBalance(balance: BalanceUpdateDto): UserReadDto {
        if (balance.amount <= 0) throw BadRequestException("Сумма пополнения должна быть больше 0")
        val user = getCurrentUser()
        user.balance += balance.amount
        userRepository.save(user)
        return user.toDto()
    }

    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return getByUsername(username)
    }

    fun getBalanceByUser(user: User): Int {
        return userRepository.getBalanceByUser(user)
    }
}