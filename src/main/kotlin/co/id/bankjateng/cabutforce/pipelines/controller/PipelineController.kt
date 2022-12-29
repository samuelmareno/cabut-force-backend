package co.id.bankjateng.cabutforce.pipelines.controller

import co.id.bankjateng.cabutforce.helper.WebResponse
import co.id.bankjateng.cabutforce.pipelines.service.PipelineService
import org.springframework.web.bind.annotation.*
import co.id.bankjateng.cabutforce.pipelines.model.*
import org.springframework.http.ResponseEntity

/**
 * @author Samuel Mareno
 * @Date 13/12/22
 */

@RestController
@RequestMapping("/api/v1/pipelines")
@CrossOrigin
class PipelineController(private val pipelineService: PipelineService) {
    @GetMapping("/")
    fun getAllPipelines(@RequestHeader("Authorization") bearer: String): ResponseEntity<WebResponse<List<PipelineResponse>>> {
        val token = bearer.substringAfter("Bearer ")
        val result = pipelineService.getAllPipelines(token)
        return ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = result))
    }

    @GetMapping("/{startDate}/{endDate}")
    fun getPipelinesBetween(
        @PathVariable startDate: Long,
        @PathVariable endDate: Long,
        @RequestHeader("Authorization") bearer: String
    ): ResponseEntity<WebResponse<List<PipelineResponse>>> {
        val token = bearer.substringAfter("Bearer ")
        val result = pipelineService.getPipelinesBetween(startDate, endDate, token)
        return ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = result))
    }

    @GetMapping("/{id}")
    fun getPipelineById(@PathVariable id: String, @RequestHeader("Authorization") bearer: String): PipelineResponse? {
        val token = bearer.substringAfter("Bearer ")
        return pipelineService.getPipelineById(id, token)
    }

    @PostMapping("/")
    fun createPipeline(
        @RequestBody createPipelineRequest: CreatePipelineRequest,
        @RequestHeader("Authorization") bearer: String
    ): PipelineResponse {
        val token = bearer.substringAfter("Bearer ")
        return pipelineService.createPipeline(createPipelineRequest, token)
    }

    @PutMapping("/")
    fun updatePipeline(
        @RequestBody updatePipelineRequest: UpdatePipelineRequest,
        @RequestHeader("Authorization") bearer: String
    ): PipelineResponse {
        val token = bearer.substringAfter("Bearer ")
        return pipelineService.updatePipeline(updatePipelineRequest, token)
    }

    @DeleteMapping("/{id}")
    fun deletePipeline(
        @PathVariable id: String,
        @RequestHeader("Authorization") bearer: String
    ) {
        val token = bearer.substringAfter("Bearer ")
        pipelineService.deletePipeline(id, token)
    }
}