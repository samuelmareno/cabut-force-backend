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
    fun getPipelineById(@PathVariable id: String, @RequestHeader("Authorization") bearer: String): ResponseEntity<WebResponse<PipelineResponse?>> {
        val token = bearer.substringAfter("Bearer ")
        val pipeline = pipelineService.getPipelineById(id, token)
        return if (pipeline == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = pipeline))
        }
    }

    @PostMapping("/")
    fun createPipeline(
        @RequestBody createPipelineRequest: CreatePipelineRequest,
        @RequestHeader("Authorization") bearer: String
    ): ResponseEntity<WebResponse<PipelineResponse>> {
        val token = bearer.substringAfter("Bearer ")
        val pipeline =  pipelineService.createPipeline(createPipelineRequest, token)
        return ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = pipeline))
    }

    @PutMapping("/")
    fun updatePipeline(
        @RequestBody updatePipelineRequest: UpdatePipelineRequest,
        @RequestHeader("Authorization") bearer: String
    ): ResponseEntity<WebResponse<PipelineResponse>> {
        val token = bearer.substringAfter("Bearer ")
        val pipeline =  pipelineService.updatePipeline(updatePipelineRequest, token)
        return ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = pipeline))
    }

    @DeleteMapping("/{id}")
    fun deletePipeline(
        @PathVariable id: String,
        @RequestHeader("Authorization") bearer: String
    ): ResponseEntity<WebResponse<String>> {
        val token = bearer.substringAfter("Bearer ")
        pipelineService.deletePipeline(id, token)
        return ResponseEntity.ok(WebResponse(status = "OK", code = 200, data = "Pipeline has been deleted"))
    }
}