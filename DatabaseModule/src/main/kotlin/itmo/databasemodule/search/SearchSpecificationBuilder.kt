package itmo.databasemodule.search

import org.springframework.data.jpa.domain.Specification

class SearchSpecificationBuilder<T> {
    private val params: MutableList<SearchCriteria> = ArrayList()
    fun with(searchCriteria: SearchCriteria): SearchSpecificationBuilder<T> {
        params.add(searchCriteria)
        return this
    }
    fun build(): Specification<T>? {
        if (params.isEmpty()) return null
        var result: Specification<T> = SearchSpecification(params[0])
        for (i in 1 until params.size) {
            val criteria = params[i]
            result = Specification.where(result).and(SearchSpecification(criteria))
        }
        return result
    }
}
