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
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@RestController
@RequestMapping("/api")
class HelloController {

    @GetMapping("/hello")
    fun hello(@RequestParam("name", defaultValue = "World") name: String): String {
        return "Hello $name"
    }

    @PostMapping("/parseSwagger")
    fun parseSwagger(@RequestBody req: ParseRequest): ParseResult {
        val result = OpenAPIParser().readLocation(req.url, null, ParseOptions().apply {
            isResolve = true
            isResolveFully = true
            isResolveCombinators = false
        })

        if(!result.messages.isEmpty()) {
            return ParseResult(req.url, emptyList(), result.messages)
        }

        val paths = result.openAPI.paths.map { path ->
            Path(path.key, path.value.readOperationsMap().map { it.key })
        }

        return ParseResult(req.url, paths, result.messages)
    }

    @PostMapping("/swaggerValidation")
    fun validate(@RequestParam("swaggerUrl") url: String,
                 @RequestParam("path") path: String,
                 @RequestParam("request") request: String,
                 @RequestParam("response") response: String): String {

        val parser = OpenAPIParser().readLocation(url, null, ParseOptions().apply {
            isResolve = true
            isResolveFully = true
            isResolveCombinators = false
        })

        parser.openAPI.paths.keys.forEach { println(it) }

        val api = OpenApiInteractionValidator.createFor(url).build()
        val req = SimpleRequest.Builder.post(path).withContentType("application/json").withBody(request).build()
        val res = SimpleResponse.Builder.ok().withContentType("application/json").withBody(response).build()

        val report = api.validate(req, res)

        return SimpleValidationReportFormat.getInstance().apply(report)
    }
}

data class ParseRequest @JsonCreator constructor(val url: String)
data class Path(val path: String, val methods: List<PathItem.HttpMethod>)
data class ParseResult(val url: String, val paths: List<Path>, val errors: List<String>)
