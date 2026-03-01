package com.napos.stepper.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.core.Roadmap

/**
 * A composable that provides a complete stepper UI, including step indicators, content, and navigation buttons.
 *
 * This is the main entry point for using the stepper library. It orchestrates the display of milestones
 * from a [Roadmap] and handles user interactions.
 *
 * @param roadmap The [Roadmap] instance that defines the structure and state of the stepper.
 * @param provider The [MilestoneScreenProvider] responsible for creating the UI for each milestone.
 * @param onSubmit A lambda to be executed when the final step is submitted.
 * @param modifier The [Modifier] to be applied to the stepper container.
 * @param properties The [StepProperties] to customize the appearance of the step indicators.
 * @param colors The [StepColors] to customize the colors of the step indicators and links.
 * @param components The [StepperComponents] to customize the different parts of the stepper.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun Stepper(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    properties: StepProperties = StepProperties.Default,
    colors: StepColors = StepColors.Default.defaultColors(),
    components: StepperComponents = StepperComponents(),
) {
    val navController = rememberNavController()

    BackHandler {
        roadmap.previous()
    }
    NavHost(
        navController = navController,
        startDestination = "preview",
    ) {
        composable("preview") {
            StepperTemplate(
                roadmap = roadmap,
                type = StepperContentType.Preview,
                onStart = {
                    navController.navigate("stepper_screen")
                },
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                modifier = modifier,
                properties = properties,
                colors = colors,
                components = components,
                {
                    PreviewScreen(
                        roadmap = roadmap,
                        provider = provider,
                    )
                },
            )
        }
        composable("stepper_screen") {
            StepperTemplate(
                roadmap = roadmap,
                type = StepperContentType.Step,
                onStart = {
                    navController.navigate("stepper_screen")
                },
                onNext = {
                    roadmap.next()
                    navController.navigate("stepper_screen")
                },
                onPrevious = {
                    roadmap.previous()
                    navController.popBackStack()
                },
                onSubmit = {
                    onSubmit()
                    roadmap.milestones.forEach { _ -> roadmap.previous() }
                    navController.navigate("preview") {
                        popUpTo("preview") { inclusive = true }
                    }
                },
                modifier = modifier,
                components = components,
                content = {
                    StepScreen(
                        screen = provider.provide(roadmap.getCurrent()),
                    )
                },
            )
        }
    }

}
