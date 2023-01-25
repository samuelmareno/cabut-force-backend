package co.id.bankjateng.cabutforce.pipelines.model

/**
 * @author Samuel Mareno
 * @Date 19/01/23
 */
data class GraphicsPipelineResponse(
    val month: String,
    val productType: String,
    val deal: Int,
    val nominal: Long,
)

