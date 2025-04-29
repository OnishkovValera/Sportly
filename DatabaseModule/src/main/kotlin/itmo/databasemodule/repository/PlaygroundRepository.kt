package itmo.databasemodule.repository

import itmo.databasemodule.domain.playground.Playground
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlaygroundRepository : JpaRepository<Playground, Int>, JpaSpecificationExecutor<Playground> {
    @Query(value = "SELECT get_average_rating(:playgroundId)", nativeQuery = true)
    fun getAvgRating(playgroundId: Int): Double?

    @Query(value = "SELECT get_playground_like_count(:playgroundId)", nativeQuery = true)
    fun getLikeCount(playgroundId: Int): Int
}