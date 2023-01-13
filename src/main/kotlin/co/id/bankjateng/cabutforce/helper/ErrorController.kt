package co.id.bankjateng.cabutforce.helper

import co.id.bankjateng.cabutforce.helper.exceptions.UserDoesNotExistException
import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.security.auth.message.AuthException
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Samuel Mareno
 * @Date 06/12/22
 */

@RestControllerAdvice
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
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

    @ExceptionHandler(value = [AuthException::class])
    fun invalidPassword(exception: AuthException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.BAD_REQUEST
        logger("ErrorController").severe(exception.message.toString())

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = exception.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [JWTVerificationException::class, MissingRequestHeaderException::class])
    fun unauthorized(exception: Exception): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.UNAUTHORIZED

        logger("ErrorController").severe(exception.message.toString())

        return ResponseEntity(
            /* body = */ WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = "Unauthorized"
            ),
            /* status = */ httpStatus
        )
    }

    @ExceptionHandler(value = [DataIntegrityViolationException::class])
    fun sqlIntegrityViolation(exception: DataIntegrityViolationException): ResponseEntity<WebResponse<String>> {
        var httpStatus = HttpStatus.BAD_REQUEST
        logger("ErrorController").severe(exception.message.toString())


        if (exception.message == "user_username_unique") {
            return ResponseEntity(
                WebResponse(
                    status = httpStatus.name,
                    code = httpStatus.value(),
                    data = "User is already exist"
                ), httpStatus
            )
        }

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = "Something went wrong"
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [NoSuchElementException::class])
    fun noSuchElement(exception: NoSuchElementException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.NOT_FOUND

        logger("ErrorController").severe(exception.message.toString())

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = exception.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun forbidden(exception: IllegalArgumentException): ResponseEntity<WebResponse<String>> {
        val httpStatus = HttpStatus.FORBIDDEN

        logger("ErrorController").severe(exception.message.toString())

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = exception.message
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun jsonParseError(exception: HttpMessageNotReadableException): ResponseEntity<WebResponse<String>> {
        logger("ErrorController").severe(exception.toString())
        val httpStatus = HttpStatus.BAD_REQUEST

        return ResponseEntity(
            WebResponse(
                status = httpStatus.name,
                code = httpStatus.value(),
                data = "Please try again later"
            ), httpStatus
        )
    }

    @ExceptionHandler(value = [Exception::class])
    fun unhandledException(exception: Exception): ResponseEntity<WebResponse<String>> {
        logger("ErrorController unhandled").severe(exception.toString())
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