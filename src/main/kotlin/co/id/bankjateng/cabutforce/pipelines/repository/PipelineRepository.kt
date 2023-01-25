package co.id.bankjateng.cabutforce.pipelines.repository

import co.id.bankjateng.cabutforce.pipelines.entity.PipelineForGraphics
import co.id.bankjateng.cabutforce.pipelines.entity.PipelineWithProductType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */
interface PipelineRepository : JpaRepository<PipelineWithProductType, String> {

    @Query(
        "SELECT p " +
                "FROM pipeline p INNER JOIN product_type pt ON p.productType.id = pt.id" +
                " WHERE p.referralUser = ?1 AND p.prospectDate BETWEEN ?2 AND ?3"
    )
    fun getPipelinesByReferralUserAndProspectDateBetweenWithProductType(
        referralUser: String,
        startDate: Long,
        endDate: Long
    ): List<PipelineWithProductType>?

    @Query(
        value = """
            SELECT DATE_FORMAT(CONVERT_TZ(FROM_UNIXTIME(prospect_date/1000), '+00:00', '+07:00'), '%m') as month, 
            pt.name as productType, SUM(status = 'DEAL') as deal, nominal
            FROM pipeline INNER JOIN product_type pt on pipeline.product_type_id = pt.id
            WHERE prospect_date BETWEEN ?1 AND ?2 AND status = 'DEAL' AND referral_user_id = ?3
            GROUP BY month, product_type_id
                """,
        nativeQuery = true
    )
    fun getGraphicsPipeline(
        startDate: Long,
        endDate: Long,
        referralUser: String
    ): List<PipelineForGraphics>?


}

