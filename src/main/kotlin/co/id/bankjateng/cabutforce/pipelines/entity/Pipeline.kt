package co.id.bankjateng.cabutforce.pipelines.entity

import jakarta.persistence.*

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

@Entity
data class Pipeline(
    @Id
    val id: String,

    val nip: String,

    val name: String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    val address: String?,

    @Column(columnDefinition = "ENUM('FOLLOW_UP', 'DEAL', 'LOST')")
    @Enumerated(EnumType.STRING)
    val status: Status,

    @Column(name = "product_type_id")
    val productType: Int,

    @Column(name = "prospect_date")
    val prospectDate: Long,

    @Column(name = "referral_user_id")
    val referralUser: String
)