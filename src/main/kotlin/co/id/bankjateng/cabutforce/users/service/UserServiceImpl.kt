package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.helper.exceptions.UserDoesNotExistException
import co.id.bankjateng.cabutforce.security.JWTUtil
import co.id.bankjateng.cabutforce.users.model.ChangePasswordRequest
import co.id.bankjateng.cabutforce.users.model.UpdateUserRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse
import co.id.bankjateng.cabutforce.users.repository.UserRepository
import co.id.bankjateng.cabutforce.validation.ValidationUtil
import com.password4j.Password
import jakarta.security.auth.message.AuthException
import org.springframework.stereotype.Service

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val validationUtil: ValidationUtil,
    private val jwtUtil: JWTUtil
) : UserService {

    override fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { user ->
            UserResponse(
                id = user.id,
                name = user.name,
                username = user.username,
                role = user.role,
                lastLogin = user.lastLogin,
                createdAt = user.createdAt
            )
        }
    }

    override fun changePassword(bearer: String, changePasswordRequest: ChangePasswordRequest): Boolean {
        val token = bearer.substringAfter("Bearer ")
        val username = jwtUtil.extractCurrentUser(token).username
        val user = userRepository.findUserByUsername(username)
            ?: throw UserDoesNotExistException("User with username $username does not exist")

        if (!Password.check(changePasswordRequest.oldPassword, user.password).withScrypt()) {
            throw AuthException("Invalid password")
        }
        val hashedPassword = Password.hash(changePasswordRequest.newPassword)
            .addRandomSalt(10)
            .withScrypt()
            .result
        userRepository.save(user.copy(password = hashedPassword))
        return true
    }

    override fun getUser(bearer: String): UserResponse {
        val token = bearer.substringAfter("Bearer ")
        val username = jwtUtil.extractCurrentUser(token).username
        val user = userRepository.findUserByUsername(username) ?: throw UserDoesNotExistException("User does not exist")
        return UserResponse(
            id = user.id,
            name = user.name,
            username = user.username,
            role = user.role,
            lastLogin = user.lastLogin,
            createdAt = user.createdAt
        )
    }

    override fun updateUser(bearer: String, updateUserRequest: UpdateUserRequest): String {
        validationUtil.validate(updateUserRequest)

        val token = bearer.substringAfter("Bearer ")
        val username = jwtUtil.extractCurrentUser(token).username
        val user = userRepository.findUserByUsername(username)
            ?: throw UserDoesNotExistException("The User with username: $username doesn't exist")

        userRepository.save(
            user.copy(
                name = updateUserRequest.name,
                username = updateUserRequest.username,
                password = updateUserRequest.password,
                role = updateUserRequest.role
            )
        )
        return "User profile has been updated"
    }

    override fun deleteUser(bearer: String) {
        val token = bearer.substringAfter("Bearer ")
        val user = jwtUtil.extractCurrentUser(token)
        userRepository.deleteById(user.id)
    }
}