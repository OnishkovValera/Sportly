package itmo.databasemodule.search

data class SearchCriteria(
    val key: String,
    val value: Any,
    val operator: SearchOperator
)