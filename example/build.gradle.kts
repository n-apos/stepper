import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compiler.compose)
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
        browser()
        nodejs()
        binaries.executable()
    }

    sourceSets {

        commonMain.dependencies {
            api(projects.core)
            api(projects.ui)

            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.uiUtil)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.jetbrains.lifecycle.compose)

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