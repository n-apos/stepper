@file:OptIn(ExperimentalDistributionDsl::class)
@file:Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compiler.compose)
}

kotlin {
    explicitApi()

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
                outputDirectory = file("${layout.buildDirectory}/wasm")
            }
        }
        binaries.executable()
    }

    sourceSets {
        sourceSets.commonMain {
            kotlin.srcDir("src/commonMain/kotlin")
            resources.srcDir("src/commonMain/composeResources")
        }


        commonMain.dependencies {
            api(projects.core)

            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.uiUtil)

            implementation(compose.components.resources)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.jetbrains.lifecycle.compose)

        }

        jvmMain.dependencies {
            implementation(compose.preview)
            implementation(compose.uiTooling)
            implementation(compose.desktop.currentOs)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.common)

            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

compose.resources {
    generateResClass = always
    publicResClass = true
    packageOfResClass = "com.napos.stepper.ui"
}