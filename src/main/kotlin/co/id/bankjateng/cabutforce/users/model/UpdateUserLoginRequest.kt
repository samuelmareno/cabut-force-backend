package co.id.bankjateng.cabutforce.users.model

import jakarta.validation.constraints.NotBlank

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */

data class UpdateUserLoginRequest(
    @field:NotBlank
    val email: String
)
