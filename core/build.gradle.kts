@file:OptIn(ExperimentalDistributionDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compiler.compose)
    alias(libs.plugins.kover)
    alias(libs.plugins.sonar)
    alias(libs.plugins.dokka)
    alias(libs.plugins.publish)
    signing
}

group = rootProject.group
version = rootProject.version

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "stepper-core", version.toString())
    pom {
        name.set("stepper")
        description.set("A simple stepper library for Compose Multiplatform.")
        url.set("https://github.com/n-apos/stepper")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("n-apos")
                name.set("N-APOS")
                email.set("contact@n-apos.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/n-apos/stepper.git")
            developerConnection.set("scm:git:ssh://github.com/n-apos/stepper.git")
            url.set("https://github.com/n-apos/stepper/tree/main")
        }
    }
}

configure<SigningExtension> {
    useGpgCmd()
    sign(extensions.getByType(PublishingExtension::class.java).publications)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    )

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            distribution {
                outputDirectory.set(layout.buildDirectory.dir("wasm"))
            }
        }
        binaries.executable()
    }

    explicitApi()

    sourceSets {

        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.material3)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.common)
        }
    }
}

kover {
    reports {
        filters {
            includes {
                packages("com.napos.stepper.core")
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
