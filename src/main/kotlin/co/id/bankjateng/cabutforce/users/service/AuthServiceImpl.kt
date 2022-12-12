package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.helper.exceptions.UserDoesNotExistException
import co.id.bankjateng.cabutforce.helper.logger
import co.id.bankjateng.cabutforce.security.JWTUtil
import co.id.bankjateng.cabutforce.users.entity.User
import co.id.bankjateng.cabutforce.users.model.RegisterUserRequest
import co.id.bankjateng.cabutforce.users.model.UserLoginRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse
import co.id.bankjateng.cabutforce.users.repository.UserRepository
import co.id.bankjateng.cabutforce.validation.ValidationUtil
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */
@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtUtil: JWTUtil,
    private val validationUtil: ValidationUtil
) : AuthService {

    init {
        logger(this.javaClass.name).info("Initializing AuthServiceImpl")
    }
    override fun register(registerUserRequest: RegisterUserRequest): UserResponse {
        validationUtil.validate(registerUserRequest)
        logger(this.javaClass.name).info("Registering user with email: ${registerUserRequest.email}")

        val user = User(
            id = UUID.randomUUID().toString(),
            name = registerUserRequest.name!!,
            email = registerUserRequest.email!!,
            password = registerUserRequest.password!!,
            role = registerUserRequest.role!!,
            lastLogin = registerUserRequest.lastLogin,
            createdAt = registerUserRequest.createdAt
        )
        userRepository.save(user)

        return UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role,
            lastLogin = user.lastLogin,
            createdAt = user.createdAt
        )
    }

    override fun login(userLoginRequest: UserLoginRequest): String {
        validationUtil.validate(userLoginRequest)

        val user = userRepository.findByEmailAndPassword(userLoginRequest.email, userLoginRequest.password)
            ?: throw UserDoesNotExistException("User does not exist")

        updateUserLogin(user.email)
        return jwtUtil.generateToken(user)
    }


    private fun updateUserLogin(email: String): String {
        val user = userRepository.findUserByEmail(email)
            ?: throw UserDoesNotExistException("The User with email: $email doesn't exist")

        userRepository.save(user.copy(lastLogin = System.currentTimeMillis()))
        return "User last login has been updated"
    }
}