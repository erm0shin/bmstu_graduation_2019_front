package dto

data class NewUser(
    val id: Long?,
    val login: String,
    val password: String,
    val email: String
)