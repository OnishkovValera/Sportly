package itmo.databasemodule.repository

import itmo.databasemodule.domain.like.EventLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventLikeRepository : JpaRepository<EventLike, Int>