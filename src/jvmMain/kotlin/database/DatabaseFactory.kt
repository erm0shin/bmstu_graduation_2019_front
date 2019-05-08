package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        // Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
        Database.connect(hikari())
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Posts)
            Posts.insert {
                it[postId] = 100000000
                it[userId] = 200000000
                it[title] = "hello_title"
                it[body] = "hello_body"
            }
//            Widgets.insert {
//                it[name] = "widget one"
//                it[quantity] = 27
//                it[dateUpdated] = System.currentTimeMillis()
//            }
//            Widgets.insert {
//                it[name] = "widget two"
//                it[quantity] = 14
//                it[dateUpdated] = System.currentTimeMillis()
//            }
        }
    }

    fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
//    config.jdbcUrl = "jdbc:h2:mem:test"
        config.jdbcUrl = "jdbc:postgresql:batman?user=batman"
        config.password = "batman"
        config.maximumPoolSize = 16
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction {
                addLogger(StdOutSqlLogger)
                block()
            }
        }

}

//fun <T> database(statement: Transaction.() -> T): T {
////    Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
//    Database.connect(hikari())
//
//    return transaction {
//        addLogger(StdOutSqlLogger)
//        statement()
//    }
//
//}