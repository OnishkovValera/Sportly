package itmo.databasemodule.repository

import itmo.databasemodule.domain.feedback.Feedback
import itmo.databasemodule.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface FeedbackRepository : JpaRepository<Feedback, Int>, JpaSpecificationExecutor<Feedback> {
    fun findAllByPlaygroundIdOrderByCreatedAtDesc(fieldId: Int, pageable: Pageable): Page<Feedback>

    fun findAllByUserOrderByCreatedAtDesc(user: User, pageable: Pageable): Page<Feedback>

    fun findAllByUserId(userId: Int, pageable: Pageable): Page<Feedback>

    fun existsByUserIdAndPlaygroundId(userId: Int, fieldId: Int): Boolean
}
