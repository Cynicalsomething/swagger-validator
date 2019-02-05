import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  id("com.moowork.node") version "1.2.0"
  id("java")
}

node {
  version = "9.2.0"
  npmVersion = "6.4.1"
  download = true
}

tasks {
  jar {
    dependsOn("npm_run_build")
    from("dist/${project.name}").into("static")
  }
}




