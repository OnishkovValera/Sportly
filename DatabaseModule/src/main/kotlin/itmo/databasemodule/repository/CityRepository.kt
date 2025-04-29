package itmo.databasemodule.repository

import itmo.databasemodule.domain.location.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Int>