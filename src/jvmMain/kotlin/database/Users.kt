package database

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = long("id").primaryKey().autoIncrement()
    val login = varchar("login", 255)
    val password = varchar("password", 255)
    val email = varchar("email", 255)
}

data class User(
    val id: Long,
    val login: String,
    val password: String,
    val email: String
)