package itmo.databasemodule.repository

import itmo.databasemodule.domain.like.PlaygroundLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaygroundLikeRepository : JpaRepository<PlaygroundLike, Int>