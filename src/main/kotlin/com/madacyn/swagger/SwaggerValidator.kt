package com.madacyn.swagger

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.model.SimpleRequest
import com.atlassian.oai.validator.model.SimpleResponse
import com.atlassian.oai.validator.report.SimpleValidationReportFormat
import com.fasterxml.jackson.annotation.JsonCreator
import io.swagger.parser.OpenAPIParser
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.parser.core.models.ParseOptions
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@RestController
@RequestMapping("/api")
class HelloController {

    @PostMapping("/parseSwagger")
    fun parseSwagger(@RequestBody req: ParseRequest): ParseResult {
        val result = OpenAPIParser().readLocation(req.url, null, ParseOptions().apply {
            isResolve = true
            isResolveFully = true
            isResolveCombinators = false
        })

        if (!result.messages.isEmpty()) {
            return ParseResult(req.url, emptyList(), result.messages)
        }

        val paths = result.openAPI.paths.map { path ->
            Path(path.key, path.value.readOperationsMap().map { it.key })
        }

        return ParseResult(req.url, paths, result.messages)
    }

    private final val jsonType = MediaType.APPLICATION_JSON

    @PostMapping("/swaggerValidation")
    fun validate(@RequestBody req: ValidateRequest): String {

        val api = OpenApiInteractionValidator.createFor(req.swaggerUrl).build()

        val request = when (req.method) {
            "GET" -> SimpleRequest.Builder.get(req.path).let {
                req.queryParams.entries.fold(it) { builder, entry ->
                    builder.withQueryParam(entry.key, entry.value)
                }
            }.build()
            "POST" -> SimpleRequest.Builder.post(req.path).withContentType(jsonType.toString()).withBody(req.request).build()
            "PUT" -> SimpleRequest.Builder.put(req.path).withContentType(jsonType.toString()).withBody(req.request).build()
            else -> throw IllegalArgumentException("${req.method} Not supported")
        }

        val res = SimpleResponse.Builder.ok().withContentType("application/json").withBody(req.response).build()

        val report = api.validate(request, res)

        return SimpleValidationReportFormat.getInstance().apply(report)
    }
}

data class ParseRequest @JsonCreator constructor(val url: String)
data class Path(val path: String, val methods: List<PathItem.HttpMethod>)
data class ParseResult(val url: String, val paths: List<Path>, val errors: List<String>)
data class ValidateRequest @JsonCreator constructor(
    val swaggerUrl: String,
    val method: String,
    val path: String,
    val request: String?,
    val queryParams: Map<String, String>,
    val response: String
)
