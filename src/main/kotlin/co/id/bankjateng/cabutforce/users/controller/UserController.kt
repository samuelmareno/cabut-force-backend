package co.id.bankjateng.cabutforce.users.controller

import co.id.bankjateng.cabutforce.helper.WebResponse
import co.id.bankjateng.cabutforce.helper.logger
import co.id.bankjateng.cabutforce.users.model.*
import co.id.bankjateng.cabutforce.users.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */

@RestController
@CrossOrigin(
    origins = ["*"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE]
)
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping(
        value = ["/all"],
        produces = ["application/json"]
    )
    fun getAllUsers(): ResponseEntity<WebResponse<List<UserResponse>>> {
        return ResponseEntity.ok(
            WebResponse(
                status = "OK",
                code = 200,
                data = userService.getAllUsers()
            )
        )
    }

    @GetMapping(
        value = ["/", ""],
        produces = ["application/json"]
    )
    fun getUser(@RequestHeader("Authorization") bearer: String): ResponseEntity<WebResponse<UserResponse>> {
        return ResponseEntity.ok(
            WebResponse(
                status = "OK",
                code = 200,
                data = userService.getUser(bearer)
            )
        )
    }

    @PutMapping(
        value = ["/", ""],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun updateUser(
        @RequestBody updateUserRequest: UpdateUserRequest,
        @RequestHeader("Authorization") bearer: String
    ): ResponseEntity<WebResponse<String>> {
        val result = userService.updateUser(bearer, updateUserRequest)
        return ResponseEntity.ok(
            WebResponse(
                status = "OK",
                code = 200,
                data = result
            )
        )
    }

    @DeleteMapping(
        value = ["/delete"],
        produces = ["application/json"]
    )
    fun deleteUser(@RequestHeader("Authorization") bearer: String): ResponseEntity<WebResponse<String>> {
        val token = bearer.substringAfter("Bearer ")
        logger("token").info(token)
        userService.deleteUser(token)
        return ResponseEntity.ok(
            WebResponse(
                status = "OK",
                code = 200,
                data = "User deleted"
            )
        )
    }

    @GetMapping(
        value = ["test"],
        produces = ["application/json"]
    )
    fun test(@RequestHeader("Authorization") bearer: String): ResponseEntity<WebResponse<UserResponse>> {
        return ResponseEntity.ok(
            WebResponse(
                status = "OK",
                code = 200,
                data = userService.getUser(bearer)
            )
        )
    }
}