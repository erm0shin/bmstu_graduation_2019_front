package database

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

object Posts : IntIdTable() {
    val postId = integer("postId")
    val userId = integer("userId")
    val title = varchar("title", 50)
    val body = varchar("body", 255)
}

data class Post(
    val postId: Int,
    val userId: Int,
    val title: String,
    val body: String
)

fun toPost(row: ResultRow): Post =
    Post(
        postId = row[Posts.postId],
        userId = row[Posts.userId],
        title = row[Posts.title],
        body = row[Posts.body]
    )