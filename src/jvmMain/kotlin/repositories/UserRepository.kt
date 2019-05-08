package repositories

import database.DatabaseFactory.dbQuery
import database.Users
import dto.NewUser
import dto.User
import org.jetbrains.exposed.sql.*

class UserRepository {

    suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map { toUser(it) }
    }

    suspend fun getUser(id: Long): User? = dbQuery {
        Users.select {
            (Users.id eq id)
        }.mapNotNull { toUser(it) }
            .singleOrNull()
    }

    suspend fun getUser(loginOrEmail: String, password: String): User? = dbQuery {
        Users.select {
            (((Users.email eq loginOrEmail) or (Users.login eq loginOrEmail))
                    and (Users.password eq password))
        }.mapNotNull { toUser(it) }
            .singleOrNull()
    }

    suspend fun userExists(loginOrEmail: String): Boolean = dbQuery {
        Users.select {
            ((Users.email eq loginOrEmail) or (Users.login eq loginOrEmail))
        }.singleOrNull() != null
    }

    suspend fun updateUser(user: NewUser): User? {
        val id = user.id
        return if (id == null) {
            addUser(user)
        } else {
            dbQuery {
                Users.update({ Users.id eq id }) {
                    it[login] = user.login
                    it[password] = user.password
                    it[email] = user.email
                }
            }
            getUser(id)
        }
    }

    suspend fun addUser(user: NewUser): User {
        var key = 0L
        dbQuery {
            key = (Users.insert {
                it[login] = user.login
                it[password] = user.password
                it[email] = user.email
            } get Users.id)!!
        }
        return getUser(key)!!
    }

    suspend fun deleteUser(id: Long): Boolean {
        return dbQuery {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }

    private fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            login = row[Users.login],
            password = row[Users.password],
            email = row[Users.email]
        )
}