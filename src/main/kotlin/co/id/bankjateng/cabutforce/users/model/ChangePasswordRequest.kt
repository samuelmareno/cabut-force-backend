package co.id.bankjateng.cabutforce.users.model

/**
 * @author Samuel Mareno
 * @Date 31/12/22
 */
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
