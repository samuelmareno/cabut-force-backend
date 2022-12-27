package co.id.bankjateng.cabutforce.pipelines.model

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

data class CreatePipelineRequest(
    val nip: String,
    val name: String,
    val phoneNumber: String,
    val address: String?,
    val status: String,
    val productType: Int,
    val prospectDate: Long,
    val referralUser: String
)