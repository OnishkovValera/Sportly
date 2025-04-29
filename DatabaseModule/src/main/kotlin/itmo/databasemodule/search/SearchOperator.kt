package itmo.databasemodule.search

enum class SearchOperator {
    CONTAINS, EQUAL, STR_EQUAL, BEFORE, AFTER,
    GREATER_THAN, LESS_THAN, NESTED_CITY_ID, NESTED_PLAYGROUND_ID, NESTED_STATION_ID
}
