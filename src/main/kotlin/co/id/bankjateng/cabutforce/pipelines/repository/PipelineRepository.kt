package co.id.bankjateng.cabutforce.pipelines.repository

import co.id.bankjateng.cabutforce.pipelines.entity.Pipeline
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */
interface PipelineRepository : JpaRepository<Pipeline, String> {

    fun getPipelinesByReferralUserAndProspectDateBetween(
        referralUser: String,
        startDate: Long,
        endDate: Long
    ): List<Pipeline>?
}