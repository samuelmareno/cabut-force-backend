package co.id.bankjateng.cabutforce.pipelines.repository

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


}

