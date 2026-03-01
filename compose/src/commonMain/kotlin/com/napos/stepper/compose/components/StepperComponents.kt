package com.napos.stepper.compose.components

import androidx.compose.runtime.Composable
import com.napos.stepper.compose.Res
import com.napos.stepper.compose.stepper_next_button
import com.napos.stepper.compose.stepper_previous_button
import com.napos.stepper.compose.stepper_submit_button
import org.jetbrains.compose.resources.stringResource

/**
 * Data class that holds the composable components used to build the stepper UI.
 * This allows for easy customization of individual parts of the stepper.
 *
 * @param step A composable lambda for rendering a single step indicator. Defaults to [Step].
 * @param stepLink A composable lambda for rendering the link between steps. Defaults to [StepLink].
 * @param startButton A composable lambda for the 'Start' button.
 * @param nextButton A composable lambda for the 'Next' button.
 * @param previousButton A composable lambda for the 'Previous' button.
 * @param submitButton A composable lambda for the 'Submit' button.
 */
public data class StepperComponents(
    val step: @Composable (StepState) -> Unit = { Step(it) },
    val stepLink: @Composable (StepState) -> Unit = { StepLink(it) },
    val startButton: @Composable (text: String, onClick: () -> Unit) -> Unit = { text, onClick ->
        StepButton(
            text = text,
            onClick = onClick,
        )
    },
    val nextButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_next_button),
            onClick = it
        )
    },
    val previousButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_previous_button),
            onClick = it
        )
    },
    val submitButton: @Composable (onClick: () -> Unit) -> Unit = {
        StepButton(
            text = stringResource(Res.string.stepper_submit_button),
            onClick = it
        )
    },
)
