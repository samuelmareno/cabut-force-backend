package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.users.model.RegisterUserRequest
import co.id.bankjateng.cabutforce.users.model.UserLoginRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */
interface AuthService {

    fun register(registerUserRequest: RegisterUserRequest): UserResponse

    fun login(userLoginRequest: UserLoginRequest): String
}