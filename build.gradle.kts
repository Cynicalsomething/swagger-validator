import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.20"
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.20"
}

group = "com.madacyn"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.atlassian.oai:swagger-request-validator-core:2.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    gradleVersion = "5.1.1"
}