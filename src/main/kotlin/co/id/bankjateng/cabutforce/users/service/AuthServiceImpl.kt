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
import com.password4j.Password
import jakarta.security.auth.message.AuthException
import org.springframework.dao.DataIntegrityViolationException
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
        logger(this.javaClass.name).info("Registering user with email: ${registerUserRequest.username}")
        val usernameIsExist = userRepository.existsUserByUsername(registerUserRequest.username!!)

        if (usernameIsExist) {
            throw DataIntegrityViolationException("user_username_unique")
        }

        val hashedPassword = Password.hash(registerUserRequest.password)
            .addRandomSalt(10)
            .withScrypt()
            .result

        val user = User(
            id = UUID.randomUUID().toString(),
            name = registerUserRequest.name!!,
            username = registerUserRequest.username,
            password = hashedPassword,
            role = registerUserRequest.role!!,
            lastLogin = registerUserRequest.lastLogin,
            createdAt = registerUserRequest.createdAt
        )
        userRepository.save(user)

        return UserResponse(
            id = user.id,
            name = user.name,
            username = user.username,
            role = user.role,
            lastLogin = user.lastLogin,
            createdAt = user.createdAt
        )
    }

    override fun login(userLoginRequest: UserLoginRequest): String {
        validationUtil.validate(userLoginRequest)

        val user = userRepository.findUserByUsername(userLoginRequest.username)
            ?: throw UserDoesNotExistException("User does not exist")

        if (!Password.check(userLoginRequest.password, user.password).withScrypt()) {
            throw AuthException("Invalid password")
        }

        updateUserLogin(user.username)
        return jwtUtil.generateToken(user)
    }

    private fun updateUserLogin(username: String): String {
        val user = userRepository.findUserByUsername(username)
            ?: throw UserDoesNotExistException("The User with username: $username doesn't exist")

        userRepository.save(user.copy(lastLogin = System.currentTimeMillis()))
        return "User last login has been updated"
    }
}