package co.id.bankjateng.cabutforce.pipelines.entity

import jakarta.persistence.*

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

@Entity(name = "pipeline")
data class PipelineWithProductType(
    @Id
    val id: String,

    val nip: String,

    val nik: String,

    val name: String,

    @Column(name = "phone_number")
    val phoneNumber: String,

    val address: String?,

    @Column(columnDefinition = "ENUM('FOLLOW_UP', 'DEAL', 'LOST')")
    @Enumerated(EnumType.STRING)
    val status: Status,

    @OneToOne(fetch = FetchType.LAZY)
    val productType: ProductType,

    @Column(name = "prospect_date")
    val prospectDate: Long,

    val nominal: Long,

    @Column(name = "referral_user_id")
    val referralUser: String
)