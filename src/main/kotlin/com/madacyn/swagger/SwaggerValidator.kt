package com.madacyn.swagger

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.model.SimpleRequest
import com.atlassian.oai.validator.model.SimpleResponse
import com.atlassian.oai.validator.report.SimpleValidationReportFormat
import io.swagger.parser.OpenAPIParser
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
class HelloController {

    @GetMapping("/api/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return "Hello $name"
    }

    @PostMapping("/api/swaggerValidation")
    fun validate(@RequestParam(value = "swaggerUrl") url: String,
                 @RequestParam(value = "path") path: String,
                 @RequestParam(value = "request") request: String,
                 @RequestParam(value = "response") response: String): String {

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

