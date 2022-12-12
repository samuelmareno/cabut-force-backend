package co.id.bankjateng.cabutforce.helper

/**
 * @author Samuel Mareno
 * @Date 05/12/22
 */
data class WebResponse<T>(
    val status: String,
    val code: Int,
    val data: T?
)
