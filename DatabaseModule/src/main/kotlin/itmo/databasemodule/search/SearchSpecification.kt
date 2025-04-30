package itmo.databasemodule.search

import itmo.databasemodule.domain.location.City
import itmo.databasemodule.domain.location.Station
import itmo.databasemodule.domain.playground.Playground
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SearchSpecification<T>(
    private val criteria: SearchCriteria
) : Specification<T> {
    override fun toPredicate(
        root: Root<T>,
        query: CriteriaQuery<*>?,
        cb: CriteriaBuilder
    ): Predicate {
        val value = criteria.value.toString().lowercase()

        return when (criteria.operator) {
            SearchOperator.CONTAINS -> cb.like(
                cb.lower(root.get(criteria.key)),
                "%$value%"
            )
            SearchOperator.EQUAL -> cb.equal(
                root.get<Any>(criteria.key),
                criteria.value
            )
            SearchOperator.STR_EQUAL -> cb.equal(
                root.get<Any>(criteria.key).`as`(String::class.java),
                criteria.value.toString()
            )
            SearchOperator.BEFORE -> {
                val date = ZonedDateTime.parse(criteria.value.toString(), DateTimeFormatter.ISO_DATE_TIME)
                cb.lessThanOrEqualTo(root.get(criteria.key), date)
            }
            SearchOperator.AFTER -> {
                val date = ZonedDateTime.parse(criteria.value.toString(), DateTimeFormatter.ISO_DATE_TIME)
                cb.greaterThanOrEqualTo(root.get(criteria.key), date)
            }
            SearchOperator.GREATER_THAN -> cb.greaterThan(
                root.get(criteria.key),
                criteria.value.toString()
            )
            SearchOperator.LESS_THAN -> cb.lessThan(
                root.get(criteria.key),
                criteria.value.toString()
            )
            SearchOperator.NESTED_CITY_ID -> {
                val cityJoin = root.join<City, T>("city")
                cb.equal(cityJoin.get<Int>("id"), criteria.value)
            }
            SearchOperator.NESTED_PLAYGROUND_ID -> {
                val fieldJoin = root.join<Playground, T>("playground")
                cb.equal(fieldJoin.get<Int>("id"), criteria.value)
            }
            SearchOperator.NESTED_STATION_ID -> {
                val stationJoin = root.join<Station, T>("stations")
                val inClause: CriteriaBuilder.In<Int> = cb.`in`(stationJoin.get("id"))
                val stationIds = (criteria.value as? List<*>)?.filterIsInstance<Int>()
                    ?: throw IllegalArgumentException("Value must be a list of integers")
                stationIds.forEach { inClause.value(it) }
                inClause
            }
        }
    }
}
