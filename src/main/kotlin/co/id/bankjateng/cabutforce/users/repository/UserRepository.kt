package co.id.bankjateng.cabutforce.users.repository

import co.id.bankjateng.cabutforce.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
interface UserRepository : JpaRepository<User, String> {

    fun findUserByEmail(email: String): User?

     fun findByEmailAndPassword(email: String, password: String): User?

}