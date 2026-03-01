package com.napos.stepper.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.napos.stepper.compose.Res
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.compose.stepper_next_button
import com.napos.stepper.compose.stepper_previous_button
import com.napos.stepper.compose.stepper_submit_button
import com.napos.stepper.core.Roadmap
import org.jetbrains.compose.resources.stringResource

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
 * @param step A composable lambda for rendering a single step indicator. Defaults to [Step].
 * @param stepLink A composable lambda for rendering the link between steps. Defaults to [StepLink].
 * @param startButton A composable lambda for the 'Start' button.
 * @param nextButton A composable lambda for the 'Next' button.
 * @param previousButton A composable lambda for the 'Previous' button.
 * @param submitButton A composable lambda for the 'Submit' button.
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
    step: @Composable (StepState) -> Unit = { Step(it) },
    stepLink: @Composable (StepState) -> Unit = { StepLink(it) },
    startButton: @Composable (text: String, onClick: () -> Unit) -> Unit = { text, onClick ->
        StepButton(
            text = text,
            onClick = onClick,
        )
    },
    nextButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_next_button),
            onClick = it
        )
    },
    previousButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_previous_button),
            onClick = it
        )
    },
    submitButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_submit_button),
            onClick = it
        )
    },
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
                provider = provider,
                type = StepperContentType.Preview,
                onStart = {
                    navController.navigate("stepper_screen")
                },
                properties = properties,
                colors = colors,
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                modifier = modifier,
                step = step,
                stepLink = stepLink,
                startButton = startButton,
                nextButton = nextButton,
                previousButton = previousButton,
                submitButton = submitButton,
            ) {
                PreviewScreen(
                    roadmap = roadmap,
                    provider = provider,
                )
            }
        }
        composable("stepper_screen") {
            StepperTemplate(
                roadmap = roadmap,
                provider = provider,
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
                step = step,
                stepLink = stepLink,
                startButton = startButton,
                nextButton = nextButton,
                previousButton = previousButton,
                submitButton = submitButton,
            ) {
                StepScreen(
                    screen = provider.provide(roadmap.getCurrent()),
                )
            }
        }
    }

}