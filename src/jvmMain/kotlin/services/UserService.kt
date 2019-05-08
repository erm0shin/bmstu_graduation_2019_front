package services

import dto.NewUser
import repositories.UserRepository

class UserService(private val userRepository: UserRepository) {

    suspend fun signup(
        newUser: NewUser
    ): Long? {
        try {
            // TODO: оптимизировать вызовы сервиса
            if (userRepository.userExists(newUser.login) or userRepository.userExists(newUser.email))
                return null
            return userRepository.addUser(newUser).id
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun signin(
        newUser: NewUser
    ): Long? {
        return try {
            (userRepository.getUser(newUser.login)?.id ?: userRepository.getUser(newUser.email)?.id)
        } catch (e: Exception) {
            null
        }
    }
}