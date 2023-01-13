package co.id.bankjateng.cabutforce.users.model

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
data class UserResponse(
    val id: String,
    val name: String,
    val username: String,
    val role: String?,
    val lastLogin: Long?,
    val createdAt: Long
)
