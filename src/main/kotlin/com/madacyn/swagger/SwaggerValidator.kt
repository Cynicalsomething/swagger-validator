package com.madacyn.swagger

import com.atlassian.oai.validator.OpenApiInteractionValidator
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

    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return "Hello $name"
    }

    @PostMapping("/swaggerValidation")
    fun validate(@RequestParam(value = "swaggerUrl") url: String,
                 @RequestParam(value = "path") path: String,
                 @RequestParam(value = "request") request: String,
                 @RequestParam(value = "response") response: String) {
        val api = OpenApiInteractionValidator.createFor(url).build()
    }
}

