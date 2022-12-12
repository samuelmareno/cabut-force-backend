package co.id.bankjateng.cabutforce.interceptors

import co.id.bankjateng.cabutforce.helper.logger
import co.id.bankjateng.cabutforce.security.JWTUtil
import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */

@Configuration
class HttpInterceptor(
    private val jwtUtil: JWTUtil
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURL.toString().contains("/auth/")) return true
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.substringAfter("Bearer ") ?: throw JWTVerificationException("Token is null")
        val user = jwtUtil.extractCurrentUser(token)
        logger("user").info(user.toString())
        return true
    }
}