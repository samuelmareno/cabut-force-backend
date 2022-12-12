package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.helper.exceptions.UserDoesNotExistException
import co.id.bankjateng.cabutforce.security.JWTUtil
import co.id.bankjateng.cabutforce.users.model.UpdateUserRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse
import co.id.bankjateng.cabutforce.users.repository.UserRepository
import co.id.bankjateng.cabutforce.validation.ValidationUtil
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
                email = user.email,
                role = user.role,
                lastLogin = user.lastLogin,
                createdAt = user.createdAt
            )
        }
    }

    override fun getUser(bearer: String): UserResponse {
        val token = bearer.substringAfter("Bearer ")
        val email = jwtUtil.extractCurrentUser(token).email
        val user = userRepository.findUserByEmail(email) ?: throw UserDoesNotExistException("User does not exist")
        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role,
            lastLogin = user.lastLogin,
            createdAt = user.createdAt
        )
    }

    override fun updateUser(bearer: String, updateUserRequest: UpdateUserRequest): String {
        validationUtil.validate(updateUserRequest)

        val token = bearer.substringAfter("Bearer ")
        val email = jwtUtil.extractCurrentUser(token).email
        val user = userRepository.findUserByEmail(email)
            ?: throw UserDoesNotExistException("The User with email: $email doesn't exist")

        userRepository.save(
            user.copy(
                name = updateUserRequest.name,
                email = updateUserRequest.email,
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