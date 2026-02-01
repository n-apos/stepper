package com.napos.stepper.compose.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

/**
 * A standardized button composable for use within the stepper component.
 *
 * This button is a wrapper around [Button] and is styled for consistency
 * across the stepper's navigation controls (e.g., Next, Previous, Submit).
 *
 * @param text The text to be displayed inside the button.
 * @param onClick A lambda to be executed when the button is clicked.
 * @param modifier The [Modifier] to be applied to the button.
 */
@Composable
public fun StepButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.testTag("step_button"),
    ) {
        Text(text = text)
    }
}
