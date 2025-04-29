package itmo.databasemodule.search

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class SearchMapper<T> {
    fun map(searchDto: SearchCriteriaDto?): Specification<T>? {
        val builder = SearchSpecificationBuilder<T>()
        val criteriaList = searchDto?.criteriaList ?: return builder.build()
        criteriaList.forEach { builder.with(it) }
        return builder.build()
    }
}
