package itmo.databasemodule.repository

import itmo.databasemodule.domain.location.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Int>