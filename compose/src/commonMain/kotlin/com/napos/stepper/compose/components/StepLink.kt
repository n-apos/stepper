package com.napos.stepper.compose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag

/**
 * A composable that renders the line connecting two steps in the stepper.
 *
 * The appearance of the link is determined by the [state] of the step it precedes
 * and is customized by the [LocalStepProperties] and [LocalStepColors] provided by the [StepperTemplate] component.
 *
 * @param state The [StepState] of the step this link connects to. The color of the link is determined by this state.
 */
@Composable
public fun StepLink(state: StepState) {
    val properties = LocalStepProperties.current
    val colors = LocalStepColors.current
    val color = when (state) {
        StepState.Passed, StepState.Current -> colors.passed
        StepState.Coming -> colors.coming
    }
    Canvas(
        modifier = Modifier
            .size(properties.size)
            .testTag("step_link"),
        onDraw = {
            drawLine(
                color = color,
//                start = Offset(x = -size.height, y = size.height / 2),
//                end = Offset(x = size.width * 2, y = size.height / 2),
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width, y = size.height / 2),
                strokeWidth = properties.strokeWidth,
            )
        }
    )
}
