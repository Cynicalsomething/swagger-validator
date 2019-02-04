plugins {
  id("java")
  id("com.moowork.node") version "1.2.0"
}

node {
  version = "9.2.0"
  npmVersion = "6.4.1"
  download = true
}

tasks.getByName<Jar>("jar") {
  dependsOn("npm_run_build")
  from("dist/swagger-validator-ui").into("static")
}
