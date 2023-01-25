package co.id.bankjateng.cabutforce.pipelines.service

import co.id.bankjateng.cabutforce.pipelines.entity.PipelineWithProductType
import co.id.bankjateng.cabutforce.pipelines.entity.Status
import co.id.bankjateng.cabutforce.pipelines.model.CreatePipelineRequest
import co.id.bankjateng.cabutforce.pipelines.model.GraphicsPipelineResponse
import co.id.bankjateng.cabutforce.pipelines.model.PipelineResponse
import co.id.bankjateng.cabutforce.pipelines.model.UpdatePipelineRequest
import co.id.bankjateng.cabutforce.pipelines.repository.PipelineRepository
import co.id.bankjateng.cabutforce.pipelines.repository.ProductTypeRepository
import co.id.bankjateng.cabutforce.security.JWTUtil
import co.id.bankjateng.cabutforce.validation.ValidationUtil
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
    private val jwtUtil: JWTUtil,
    private val validation: ValidationUtil
) : PipelineService {

    override fun getAllPipelines(token: String): List<PipelineResponse> {
        jwtUtil.extractCurrentUser(token)

        return pipelineRepository.findAll().map { pipeline ->
            PipelineResponse(
                id = pipeline.id,
                nip = pipeline.nip,
                nik = pipeline.nik,
                name = pipeline.name,
                referralUser = pipeline.referralUser,
                prospectDate = pipeline.prospectDate,
                status = pipeline.status,
                address = pipeline.address,
                phoneNumber = pipeline.phoneNumber,
                productType = pipeline.productType,
                nominal = pipeline.nominal
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
                    nik = pipeline.nik,
                    name = pipeline.name,
                    referralUser = pipeline.referralUser,
                    prospectDate = pipeline.prospectDate,
                    status = pipeline.status,
                    address = pipeline.address,
                    phoneNumber = pipeline.phoneNumber,
                    productType = pipeline.productType,
                    nominal = pipeline.nominal
                )
            } ?: emptyList()
    }

    override fun getPipelinesBetweenGraphics(
        startDate: Long,
        endDate: Long,
        token: String
    ): List<GraphicsPipelineResponse>? {
        val user = jwtUtil.extractCurrentUser(token)

        val result = pipelineRepository.getGraphicsPipeline(1673802000000, 1674061200000, user.id)

        return result?.map {
            GraphicsPipelineResponse(
                month = it.month,
                productType = it.productType,
                deal = it.deal,
                nominal = it.nominal
            )
        }
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
            nik = pipeline.nik,
            name = pipeline.name,
            referralUser = pipeline.referralUser,
            prospectDate = pipeline.prospectDate,
            status = pipeline.status,
            address = pipeline.address,
            phoneNumber = pipeline.phoneNumber,
            productType = pipeline.productType,
            nominal = pipeline.nominal
        )
    }

    override fun createPipeline(createPipelineRequest: CreatePipelineRequest, token: String): PipelineResponse {
        validation.validate(createPipelineRequest)
        val user = jwtUtil.extractCurrentUser(token)
        val productType = productTypeRepository.findById(createPipelineRequest.productType).get()

        val pipeline = PipelineWithProductType(
            id = UUID.randomUUID().toString(),
            nip = createPipelineRequest.nip,
            nik = createPipelineRequest.nik,
            name = createPipelineRequest.name,
            referralUser = user.id,
            prospectDate = createPipelineRequest.prospectDate,
            status = Status.valueOf(createPipelineRequest.status.uppercase(Locale.getDefault())),
            address = createPipelineRequest.address,
            phoneNumber = createPipelineRequest.phoneNumber,
            productType = productType,
            nominal = createPipelineRequest.nominal
        )
        val savedPipeline = pipelineRepository.save(pipeline)
        return PipelineResponse(
            id = savedPipeline.id,
            nip = savedPipeline.nip,
            nik = savedPipeline.nik,
            name = savedPipeline.name,
            referralUser = savedPipeline.referralUser,
            prospectDate = savedPipeline.prospectDate,
            status = savedPipeline.status,
            address = savedPipeline.address,
            phoneNumber = savedPipeline.phoneNumber,
            productType = savedPipeline.productType,
            nominal = savedPipeline.nominal
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
            nik = updatePipelineRequest.nik,
            name = updatePipelineRequest.name,
            prospectDate = updatePipelineRequest.prospectDate,
            status = Status.valueOf(updatePipelineRequest.status.uppercase(Locale.getDefault())),
            address = updatePipelineRequest.address,
            phoneNumber = updatePipelineRequest.phoneNumber,
            productType = productType,
            nominal = updatePipelineRequest.nominal
        )
        val savedPipeline = pipelineRepository.save(updatedPipeline)
        return PipelineResponse(
            id = savedPipeline.id,
            nip = savedPipeline.nip,
            nik = savedPipeline.nik,
            name = savedPipeline.name,
            referralUser = savedPipeline.referralUser,
            prospectDate = savedPipeline.prospectDate,
            status = savedPipeline.status,
            address = savedPipeline.address,
            phoneNumber = savedPipeline.phoneNumber,
            productType = savedPipeline.productType,
            nominal = savedPipeline.nominal
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