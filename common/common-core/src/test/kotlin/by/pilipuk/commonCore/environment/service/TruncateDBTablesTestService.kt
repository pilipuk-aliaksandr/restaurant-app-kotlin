package by.pilipuk.commonCore.environment.service

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.PreparedStatement

@Service
class TruncateDBTablesTestService(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    companion object {
        const val TRUNCATE_TABLES = "TRUNCATE TABLE %s RESTART IDENTITY CASCADE;"

        val TABLES_NAMES = listOf(
            "orders.orders",
            "orders.order_items",
            "kitchens.orders",
            "kitchens.order_items",
            "users.users"
        )
    }

    fun truncateAllTables() {
        TABLES_NAMES.forEach { table ->
            jdbcTemplate.execute(
                TRUNCATE_TABLES.format(table)
            ) { ps: PreparedStatement -> ps.execute() }
        }
    }
}