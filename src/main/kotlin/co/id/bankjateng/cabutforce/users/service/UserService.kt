package co.id.bankjateng.cabutforce.users.service

import co.id.bankjateng.cabutforce.users.model.*

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
interface UserService {

    fun getAllUsers(): List<UserResponse>

    fun getUser(bearer: String): UserResponse
    fun updateUser(bearer: String, updateUserRequest: UpdateUserRequest): String
    fun changePassword(bearer: String, changePasswordRequest: ChangePasswordRequest): Boolean
    fun deleteUser(bearer: String)
}