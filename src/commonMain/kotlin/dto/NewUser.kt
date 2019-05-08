package dto

import kotlinx.serialization.Serializable

@Serializable
data class NewUser(
    val id: Long?,
    val login: String,
    val password: String,
    val email: String
)