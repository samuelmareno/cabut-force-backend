package co.id.bankjateng.cabutforce.security

import co.id.bankjateng.cabutforce.users.entity.User
import co.id.bankjateng.cabutforce.users.model.CurrentUserResponse
import co.id.bankjateng.cabutforce.users.repository.UserRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */

@Component
class JWTUtil(private val userRepository: UserRepository) {
    // Injects the jwt-secret property set in the resources/application.properties file
    @Value("\${jwt-secret}")
    private lateinit var jwtSecret: String

    // Generates a JWT token
    fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("User Details")
            .withIssuer("Cabut Force")
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withClaim("name", user.name)
            .withClaim("role", user.role)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    @Throws(JWTVerificationException::class)
    fun extractCurrentUser(token: String): CurrentUserResponse {
        val jwt = JWT.require(Algorithm.HMAC256(jwtSecret))
            .withSubject("User Details")
            .withIssuer("Cabut Force")
            .build()
            .verify(token)
        return CurrentUserResponse(
            id = jwt.getClaim("id").asString(),
            name = jwt.getClaim("name").asString(),
            email = jwt.getClaim("email").asString(),
            role = jwt.getClaim("role").asString()
        )

    }
}