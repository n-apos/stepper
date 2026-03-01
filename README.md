# Stepper

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=n-apos_stepper&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=n-apos_stepper)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=n-apos_stepper&metric=coverage)](https://sonarcloud.io/summary/new_code?id=n-apos_stepper)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=n-apos_stepper&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=n-apos_stepper)

[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=n-apos_stepper&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=n-apos_stepper)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=n-apos_stepper&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=n-apos_stepper)

A Kotlin Multiplatform project demonstrating a custom stepper component built with Jetpack Compose.

## 🚀 Features

* **Kotlin Multiplatform:** Shared codebase for Android, iOS, Desktop, and Web.
* **Jetpack Compose:** Modern UI toolkit for building native interfaces.
* **Dependency Injection:** Using Koin for managing dependencies.
* **Asynchronous Programming:** Using Kotlin Coroutines.
* **Serialization:** Using kotlinx.serialization for data serialization.

## Modules

This project is divided into the following modules:

*   `:core`: Contains the core business logic of the stepper.
*   `:compose`: Contains the Composable UI components for the stepper.
*   `:example`: An example application showcasing the usage of the stepper component.

## 🛠️ Build and Run

To build and run the project, you will need Android Studio.

### Desktop

1.  Open a terminal in the project's root directory.
2.  Run the following Gradle command:
    ```bash
    ./gradlew :example:run
    ```

### Web

1.  Open a terminal in the project's root directory.
2.  Run the following Gradle command:
    ```bash
    ./gradlew :example:wasmJsBrowserDevelopmentRun
    ```
3.  Open your web browser and navigate to `http://localhost:8080`.

## Usage

To use this library in your project, add the following dependencies to your `build.gradle.kts` file:

```kotlin
dependencies {
    // For the core business logic
    implementation("com.n-apos.stepper:stepper-core:0.2.0")

    // For the Compose UI components
    implementation("com.n-apos.stepper:stepper-compose:0.2.0")
}
```

### Core Module

The `core` module provides the main logic for the stepper. Here's how you can use it:

```kotlin
// 1. Define your data classes that hold the data for each step.
// These should implement MilestoneData.
@Serializable
data class FirstData(val name: String) : MilestoneData()
// ... define SecondData, ThirdData, etc.

// 2. Define your Milestones by extending the Milestone class.
class FirstMilestone(
    override var data: FirstData? = null,
    override var next: Milestone<*>? = null,
    override var previous: Milestone<*>? = null,
) : Milestone<FirstData>()
// ... define SecondMilestone, ThirdMilestone, etc.

// 3. Create a Roadmap using the DSL.
val roadmap = Roadmap {
    // The roadmap can be configured with a kotlinx.serialization Json instance
    // if you have polymorphic data classes.
    // configuration = json

    // Add instances of your milestone classes in the desired order.
    milestones += listOf(
        FirstMilestone(),
        SecondMilestone(),
        ThirdMilestone()
    )
}

// 4. As the user progresses, you fill the milestones with data.
roadmap.milestones.first().fill(FirstData("John"))

// 5. You can then aggregate all the data from the milestones into a single data class.
@Serializable
data class AggregatedResult(
    val first: FirstData,
    val second: SecondData,
    val third: ThirdData
)
val result = roadmap.aggregate<AggregatedResult>()
```

### Compose Module

The `compose` module provides the UI for the stepper. Here's how you can use it with Jetpack Compose:

```kotlin
// 1. Create a Roadmap instance (see Core Module example).
val roadmap: Roadmap = { } // ... same as above

// 2. Create a class that implements MilestoneScreenProvider.
// This class is responsible for mapping a given milestone to its Composable screen.
class MyScreenProvider : MilestoneScreenProvider() {
    override fun provide(milestone: Milestone<*>): MilestoneScreen<*, *> {
        return when (milestone) {
            is FirstMilestone -> FirstScreen(milestone)
            is SecondMilestone -> SecondScreen(milestone)
            is ThirdMilestone -> ThirdScreen(milestone)
            else -> error("Milestone not supported: ${milestone::class.simpleName}")
        }
    }
}

// FirstScreen, SecondScreen, etc. are implementations of MilestoneScreen
// which provides the actual @Composable content for that step.
// For example:
class FirstScreen(
    override val milestone: FirstMilestone, 
    override val viewModel: MilestoneViewModel
) : MilestoneScreen<FirstMilestone, MilestoneViewModel> {
    
    @Composable
    override fun title(): String = "First Step"
    
    @Composable
    override fun render() {
        // Build your UI for the "First" step here.
        Text("This is the UI for the first step: ${title()}")
    }
}

// 3. In your UI, instantiate your provider and use the Stepper composable.
val myScreenProvider = remember { MyScreenProvider() }

Stepper(
    roadmap = roadmap,
    provider = myScreenProvider,
    onSubmit = {
        // This is called when the user clicks the final submit button.
        val result = roadmap.aggregate<AggregatedResult>()
        println("All steps complete! $result")
    },
)
```

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.
