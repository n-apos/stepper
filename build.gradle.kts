import org.jetbrains.changelog.date
import org.sonarqube.gradle.SonarTask

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compiler.compose) apply false
    alias(libs.plugins.publish) apply false
    alias(libs.plugins.changelog)
    alias(libs.plugins.version)
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
    alias(libs.plugins.dokka)
    signing
}


group = "com.n-apos.stepper"

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

semanticVersion {
    location = "version.properties"
    rootProject.version = resolvedVersion
}

changelog {
    version = rootProject.version.toString()
    header = provider { "[${version.get()}] - ${date()}" }
}

dependencies {
    kover(projects.core)
    kover(projects.compose)

    dokka(projects.core)
    dokka(projects.compose)
}

tasks.register<Copy>("generateReadme") {
    from("doc/README.template.md")
    into(projectDir)
    rename { "README.md" }
    expand("version" to project.version)
}