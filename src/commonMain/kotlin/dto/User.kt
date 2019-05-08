package dto

data class User(
    val id: Long,
    val login: String,
    val password: String,
    val email: String
)