package co.id.bankjateng.cabutforce.users.model

import jakarta.validation.constraints.NotBlank

/**
 * @author Samuel Mareno
 * @Date 11/12/22
 */
data class UserLoginRequest(

    @field:NotBlank(message = "Email cannot be empty")
    val email: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
)