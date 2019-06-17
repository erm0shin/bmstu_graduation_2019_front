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
            (userRepository.getUser(newUser.login, newUser.password)?.id ?:
                 userRepository.getUser(newUser.email, newUser.password)?.id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun userExists(
        userId: Long
    ): Boolean {
        return userRepository.getUser(userId) != null
    }
}