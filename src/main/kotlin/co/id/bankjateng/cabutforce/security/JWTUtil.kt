package co.id.bankjateng.cabutforce.security

import co.id.bankjateng.cabutforce.users.entity.User
import co.id.bankjateng.cabutforce.users.model.CurrentUserResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */

@Component
class JWTUtil {

    private val jwtSecret: String = System.getenv("JWT_SECRET")

    // Generates a JWT token
    fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("User Details")
            .withIssuer("Cabut Force")
            .withClaim("id", user.id)
            .withClaim("username", user.username)
            .withClaim("name", user.name)
            .withClaim("role", user.role)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
            .sign(Algorithm.HMAC256(jwtSecret))
    }

    // Verifies a JWT token and returns the user details
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
            username = jwt.getClaim("username").asString(),
            role = jwt.getClaim("role").asString()
        )
    }
}