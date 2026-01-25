@file:OptIn(ExperimentalDistributionDsl::class)
@file:Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compiler.compose)
    alias(libs.plugins.sonar)
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
            commonWebpackConfig {
                outputFileName = "n-stepper-example.js"
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
            api(projects.compose)

            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.uiUtil)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.jetbrains.lifecycle.compose)

            implementation(compose.components.resources)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.uiTooling)
            implementation(compose.preview)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.common)
        }
    }
}

compose.resources {
    generateResClass = always
    publicResClass = true
    packageOfResClass = "com.napos.stepper.example"
}

sonar {
    isSkipProject = true
}
