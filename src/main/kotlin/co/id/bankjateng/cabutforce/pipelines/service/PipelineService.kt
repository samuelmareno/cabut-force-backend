package co.id.bankjateng.cabutforce.pipelines.service

import co.id.bankjateng.cabutforce.pipelines.model.CreatePipelineRequest
import co.id.bankjateng.cabutforce.pipelines.model.PipelineResponse
import co.id.bankjateng.cabutforce.pipelines.model.UpdatePipelineRequest

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */
interface PipelineService {
    fun getAllPipelines(token: String): List<PipelineResponse>
    fun getPipelinesBetween(startDate: Long, endDate: Long, token: String): List<PipelineResponse>
    fun getPipelineById(id: String, token: String): PipelineResponse?
    fun createPipeline(createPipelineRequest: CreatePipelineRequest, token: String): PipelineResponse
    fun updatePipeline(updatePipelineRequest: UpdatePipelineRequest, token: String): PipelineResponse
    fun deletePipeline(id: String, token: String)
}