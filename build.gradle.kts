import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.konan.properties.Properties
import org.sonarqube.gradle.SonarTask

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compiler.compose) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}


group = "com.n-apos.stepper"
version = getVersion()


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    tasks.register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(
            extensions.findByType<KotlinMultiplatformExtension>()
                ?.sourceSets
                ?.flatMap { it.kotlin }
                ?: emptyList<SourceDirectorySet>()
        )
    }
}

//subprojects {
//    apply(plugin = "maven-publish")
//    apply(plugin = "signing")
//
//
//    tasks.register<Jar>("dokkaJar") {
//        archiveClassifier.set("javadoc")
//        from(tasks.named("dokkaHtml"))
//    }
//
//    afterEvaluate {
//        extensions.configure<PublishingExtension> {
//            repositories {
//                maven {
//                    name = "sonatype"
//                    val isSnapshot = version.toString().endsWith("SNAPSHOT")
//                    url = if (isSnapshot) {
//                        uri("https://central.sonatype.com/repository/maven-snapshots/")
//                    } else {
//                        uri("https://central.sonatype.com/repository/maven-releases/")
//                    }
//                    credentials {
//                        username = System.getenv("SONATYPE_USERNAME") ?: project.findProperty("sonatypeUsername") as? String
//                        password = System.getenv("SONATYPE_PASSWORD") ?: project.findProperty("sonatypePassword") as? String
//                    }
//                }
//            }
//            publications.withType<MavenPublication> {
//                artifact(tasks.named("dokkaJar"))
//                pom {
//                    name.set("stepper")
//                    groupId = "com.n-apos"
//                    description.set("A simple stepper library for Compose Multiplatform.")
//                    url.set("https://github.com/n-apos/stepper")
//                    licenses {
//                        license {
//                            name.set("The Apache License, Version 2.0")
//                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                        }
//                    }
//                    developers {
//                        developer {
//                            id.set("n-apos")
//                            name.set("N-APOS")
//                            email.set("contact@n-apos.com")
//                        }
//                    }
//                    scm {
//                        connection.set("scm:git:github.com/n-apos/stepper.git")
//                        developerConnection.set("scm:git:ssh://github.com/n-apos/stepper.git")
//                        url.set("https://github.com/n-apos/stepper/tree/main")
//                    }
//                }
//            }
//        }
//
//        project.extensions.configure<SigningExtension> {
//            useGpgCmd()
//            val publishing = project.extensions.getByType(PublishingExtension::class.java)
//            sign(publishing.publications)
//        }
//    }
//}

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

    dokka(projects.core)
    dokka(projects.compose)
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
