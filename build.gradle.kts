import org.gradle.kotlin.dsl.withType
import org.sonarqube.gradle.SonarTask

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compiler.compose) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
}


group = "com.napos"
version = "1.0-SNAPSHOT"

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
        property("sonar.host.url", "https://sonarcloud.io")

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