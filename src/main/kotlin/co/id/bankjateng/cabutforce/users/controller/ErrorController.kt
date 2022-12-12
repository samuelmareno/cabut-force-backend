package co.id.bankjateng.cabutforce.users.controller

import co.id.bankjateng.cabutforce.helper.WebResponse
import co.id.bankjateng.cabutforce.helper.exceptions.UserDoesNotExistException
import co.id.bankjateng.cabutforce.helper.logger
import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.validation.ConstraintViolationException
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Samuel Mareno
 * @Date 06/12/22
 */

@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun validationHandler(constraintViolationException: ConstraintViolationException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.BAD_REQUEST

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = constraintViolationException.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [UserDoesNotExistException::class])
    fun userNotFoundHandler(userDoesNotExistException: UserDoesNotExistException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.BAD_REQUEST

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = userDoesNotExistException.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [JWTVerificationException::class])
    fun unauthorized(jwtVerification: JWTVerificationException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.UNAUTHORIZED

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = jwtVerification.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [DataIntegrityViolationException::class])
    fun sqlIntegrityViolation(exception: DataIntegrityViolationException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.BAD_REQUEST

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = "User is already exist"
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [Exception::class])
    fun unhandledException(exception: Exception): ResponseEntity<WebResponse<String>> {
        logger("error").warning(exception.toString())
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = "Something went wrong"
            ), httpStatus
        )
    }
}