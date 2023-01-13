package co.id.bankjateng.cabutforce.pipelines.model

import jakarta.validation.constraints.NotBlank

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

data class CreatePipelineRequest(

    @field:NotBlank
    val nip: String,

    @field:NotBlank
    val nik: String,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val phoneNumber: String,

    val address: String?,

    @field:NotBlank
    val status: String,


    val productType: Int,

    val prospectDate: Long,

    val nominal: Long
)