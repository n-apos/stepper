plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "stepper"

include(
    "core",
    "compose",
    "example",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
