package co.id.bankjateng.cabutforce.pipelines.model

import co.id.bankjateng.cabutforce.pipelines.entity.ProductType
import co.id.bankjateng.cabutforce.pipelines.entity.Status

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

data class PipelineResponse(
    val id: String,
    val nip: String,
    val name: String,
    val phoneNumber: String,
    val address: String?,
    val status: Status,
    val productType: ProductType,
    val prospectDate: Long,
    val referralUser: String
)