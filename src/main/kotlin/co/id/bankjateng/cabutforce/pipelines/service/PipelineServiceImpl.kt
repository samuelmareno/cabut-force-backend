package co.id.bankjateng.cabutforce.pipelines.service

import co.id.bankjateng.cabutforce.pipelines.entity.PipelineWithProductType
import co.id.bankjateng.cabutforce.pipelines.entity.Status
import co.id.bankjateng.cabutforce.pipelines.model.CreatePipelineRequest
import co.id.bankjateng.cabutforce.pipelines.model.PipelineResponse
import co.id.bankjateng.cabutforce.pipelines.model.UpdatePipelineRequest
import co.id.bankjateng.cabutforce.pipelines.repository.PipelineRepository
import co.id.bankjateng.cabutforce.pipelines.repository.ProductTypeRepository
import co.id.bankjateng.cabutforce.security.JWTUtil
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

@Service
class PipelineServiceImpl(
    private val pipelineRepository: PipelineRepository,
    private val productTypeRepository: ProductTypeRepository,
    private val jwtUtil: JWTUtil
) : PipelineService {
    override fun getAllPipelines(token: String): List<PipelineResponse> {
        jwtUtil.extractCurrentUser(token)

        return pipelineRepository.findAll().map { pipeline ->
            PipelineResponse(
                id = pipeline.id,
                nip = pipeline.nip,
                name = pipeline.name,
                referralUser = pipeline.referralUser,
                prospectDate = pipeline.prospectDate,
                status = pipeline.status,
                address = pipeline.address,
                phoneNumber = pipeline.phoneNumber,
                productType = pipeline.productType
            )
        }
    }

    override fun getPipelinesBetween(
        startDate: Long,
        endDate: Long,
        token: String
    ): List<PipelineResponse> {
        val user = jwtUtil.extractCurrentUser(token)

        return pipelineRepository.getPipelinesByReferralUserAndProspectDateBetweenWithProductType(
            user.id,
            startDate,
            endDate
        )
            ?.map { pipeline ->
                PipelineResponse(
                    id = pipeline.id,
                    nip = pipeline.nip,
                    name = pipeline.name,
                    referralUser = pipeline.referralUser,
                    prospectDate = pipeline.prospectDate,
                    status = pipeline.status,
                    address = pipeline.address,
                    phoneNumber = pipeline.phoneNumber,
                    productType = pipeline.productType
                )
            } ?: emptyList()
    }

    override fun getPipelineById(id: String, token: String): PipelineResponse? {
        jwtUtil.extractCurrentUser(token)

        val pipeline = try {
            pipelineRepository.findById(id).get()
        } catch (e: NoSuchElementException) {
            return null
        }
        return PipelineResponse(
            id = pipeline.id,
            nip = pipeline.nip,
            name = pipeline.name,
            referralUser = pipeline.referralUser,
            prospectDate = pipeline.prospectDate,
            status = pipeline.status,
            address = pipeline.address,
            phoneNumber = pipeline.phoneNumber,
            productType = pipeline.productType
        )
    }

    override fun createPipeline(createPipelineRequest: CreatePipelineRequest, token: String): PipelineResponse {
        val user = jwtUtil.extractCurrentUser(token)
        val productType = productTypeRepository.findById(createPipelineRequest.productType).get()

        val pipeline = PipelineWithProductType(
            id = UUID.randomUUID().toString(),
            nip = createPipelineRequest.nip,
            name = createPipelineRequest.name,
            referralUser = user.id,
            prospectDate = createPipelineRequest.prospectDate,
            status = Status.valueOf(createPipelineRequest.status.uppercase(Locale.getDefault())),
            address = createPipelineRequest.address,
            phoneNumber = createPipelineRequest.phoneNumber,
            productType = productType,
        )
        val savedPipeline = pipelineRepository.save(pipeline)
        return PipelineResponse(
            id = savedPipeline.id,
            nip = savedPipeline.nip,
            name = savedPipeline.name,
            referralUser = savedPipeline.referralUser,
            prospectDate = savedPipeline.prospectDate,
            status = savedPipeline.status,
            address = savedPipeline.address,
            phoneNumber = savedPipeline.phoneNumber,
            productType = savedPipeline.productType
        )
    }

    override fun updatePipeline(
        updatePipelineRequest: UpdatePipelineRequest,
        token: String
    ): PipelineResponse {
        val user = jwtUtil.extractCurrentUser(token)
        val id = updatePipelineRequest.id
        val pipeline = getPipelineById(id)

        if (pipeline.referralUser != user.id) {
            throw IllegalArgumentException("Pipeline with id $id is not owned by ${user.id}")
        }
        val productType = productTypeRepository.findById(updatePipelineRequest.productType).get()


        val updatedPipeline = pipeline.copy(
            nip = updatePipelineRequest.nip,
            name = updatePipelineRequest.name,
            prospectDate = updatePipelineRequest.prospectDate,
            status = Status.valueOf(updatePipelineRequest.status.uppercase(Locale.getDefault())),
            address = updatePipelineRequest.address,
            phoneNumber = updatePipelineRequest.phoneNumber,
            productType = productType
        )
        val savedPipeline = pipelineRepository.save(updatedPipeline)
        return PipelineResponse(
            id = savedPipeline.id,
            nip = savedPipeline.nip,
            name = savedPipeline.name,
            referralUser = savedPipeline.referralUser,
            prospectDate = savedPipeline.prospectDate,
            status = savedPipeline.status,
            address = savedPipeline.address,
            phoneNumber = savedPipeline.phoneNumber,
            productType = savedPipeline.productType
        )
    }

    override fun deletePipeline(
        id: String,
        token: String
    ) {
        val user = jwtUtil.extractCurrentUser(token)

        val pipeline = getPipelineById(id)
        if (pipeline.referralUser != user.id) {
            throw IllegalArgumentException("Pipeline with id $id is not owned by ${user.id}")
        }
        pipelineRepository.deleteById(id)
    }

    private fun getPipelineById(id: String): PipelineWithProductType {
        return try {
            pipelineRepository.findById(id).get()
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("Pipeline with id $id not found")
        }
    }
}