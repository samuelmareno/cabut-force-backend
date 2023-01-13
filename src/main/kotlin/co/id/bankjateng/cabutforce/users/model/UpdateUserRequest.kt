package co.id.bankjateng.cabutforce.users.model

import jakarta.validation.constraints.Null

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */

data class UpdateUserRequest(

    val name: String,
    val username: String,

    @field:Null
    val password: String?,

    val role: String
)
