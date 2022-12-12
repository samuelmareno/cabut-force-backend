package co.id.bankjateng.cabutforce.users.model

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
data class CurrentUserResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: String?,
)
