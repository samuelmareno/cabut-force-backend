package co.id.bankjateng.cabutforce.users.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */

@Entity
@Table(name = "user")
data class User(
    @Id
    val id: String,

    @Column
    val name: String,

    @Column
    val username: String,

    @Column
    val password: String?,

    @Column
    val role: String,

    @Column(name = "last_login")
    val lastLogin: Long?,

    @Column(name = "created_at")
    val createdAt: Long
)
