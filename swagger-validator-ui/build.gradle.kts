import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  id("com.moowork.node") version "1.2.0"
}

node {
  version = "9.2.0"
  npmVersion = "6.4.1"
  download = true
}

