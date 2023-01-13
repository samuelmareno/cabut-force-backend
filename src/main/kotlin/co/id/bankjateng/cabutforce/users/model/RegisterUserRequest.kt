package co.id.bankjateng.cabutforce.users.model

import jakarta.validation.constraints.NotBlank

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
data class RegisterUserRequest(

    @field:NotBlank
    val name: String?,

    @field:NotBlank
    val username: String?,

    @field:NotBlank
    val password: String?,

    @field:NotBlank
    val role: String?,

    val lastLogin: Long? = null,

    val createdAt: Long = System.currentTimeMillis()
)
