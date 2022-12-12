package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.users.model.RegisterUserRequest
import co.id.bankjateng.cabutforce.users.model.UpdateUserRequest
import co.id.bankjateng.cabutforce.users.model.UserLoginRequest
import co.id.bankjateng.cabutforce.users.model.UserResponse

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
interface UserService {

    fun getAllUsers(): List<UserResponse>

    fun getUser(bearer: String): UserResponse
    fun updateUser(bearer: String, updateUserRequest: UpdateUserRequest): String
    fun deleteUser(bearer: String)
}