import org.jetbrains.kotlin.konan.properties.Properties
import org.sonarqube.gradle.SonarTask

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compiler.compose) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
}


group = "com.napos"
version = getVersion()


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

kover {
    reports {
        filters {
            includes {
                packages("com.napos.stepper")
            }
        }
        total {
            xml {
                require(true)
                xmlFile = layout.buildDirectory.file("reports/kover/coverage.xml")
            }
            html {
                require(true)
            }
        }
    }
}

sonar {
    properties {
        property("sonar.organization", "n-apos")
        property("sonar.projectKey", "n-apos_stepper")
        property("sonar.projectName", "stepper")
        property("sonar.projectVersion", version)
        val url = System.getenv("SONAR_HOST_URL") ?: properties["SONAR_HOST_URL"] as String
        property("sonar.host.url", url)

        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.get()}/reports/kover/coverage.xml"
        )
    }
}

tasks.withType<SonarTask> {
    dependsOn("koverXmlReport")
}

dependencies {
    kover(projects.core)
    kover(projects.compose)
}

// Custom
fun getVersion(): String =
    with(Properties()) {
        load(file("version.properties").reader())
        this["version"] as String
    }

tasks.register("version") {
    doLast {
        println("version=$version")
    }
}

tasks.register("setVersion") {
    doLast {
        val version = properties["version-name"] as? String
        if (version != null) {
            with(Properties()) {
                load(file("version.properties").reader())
                this["version"] = version
                store(file("version.properties").writer(), null)
            }
        } else {
            error("version-name property not found")
        }

    }
}