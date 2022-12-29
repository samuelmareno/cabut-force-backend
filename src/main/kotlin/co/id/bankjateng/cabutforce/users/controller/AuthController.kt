package co.id.bankjateng.cabutforce.users.controller

import co.id.bankjateng.cabutforce.helper.WebResponse
import co.id.bankjateng.cabutforce.users.model.RegisterUserRequest
import co.id.bankjateng.cabutforce.users.model.UserLoginRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse
import co.id.bankjateng.cabutforce.users.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping(
        value = ["/register"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun registerUser(@RequestBody registerUserRequest: RegisterUserRequest): ResponseEntity<WebResponse<UserResponse>> {
        val userResponse = authService.register(registerUserRequest)

        return ResponseEntity.created(URI("/api/users/register"))
            .body(WebResponse("CREATED", 201, userResponse))
    }

    @PostMapping(
        value = ["/login"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun loginUser(@RequestBody userLoginRequest: UserLoginRequest): ResponseEntity<WebResponse<HashMap<String, String>>> {
        val login = authService.login(userLoginRequest)
        val map = HashMap<String, String>()
        map["token"] = login
        return ResponseEntity.ok(WebResponse("OK", 200, map))
    }
}