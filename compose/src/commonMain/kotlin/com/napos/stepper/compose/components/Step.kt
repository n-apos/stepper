package com.napos.stepper.compose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Represents the visual state of a step in the stepper.
 */
public enum class StepState {
    /**
     * The step has been successfully completed.
     */
    Passed,

    /**
     * The step is the current active step.
     */
    Current,

    /**
     * The step has not yet been reached.
     */
    Coming
}


/**
 * A composable that renders a single step indicator in the stepper.
 *
 * The appearance of the step is determined by its [state] and customized by the [LocalStepProperties]
 * and [LocalStepColors] provided by the [StepperTemplate] component.
 *
 * @param state The current [StepState] of the step (e.g., Passed, Current, Coming).
 * @param contentDescription An optional description for accessibility services.
 */
@Composable
public fun Step(
    state: StepState,
    contentDescription: String? = null,
) {
    val properties = LocalStepProperties.current
    val colors = LocalStepColors.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Canvas(
            modifier = Modifier
                .size(properties.size)
                .testTag("step")
                .semantics {
                    contentDescription?.let { this.contentDescription = it }
                }
        ) {
            when (state) {
                StepState.Passed -> {
                    drawCircle(
                        color = colors.passed,
                        style = Stroke(
                            width = properties.strokeWidth,
                        ),
                    )
                    drawCircle(
                        color = colors.passed,
                        radius = size.width / 2,
                    )
                }

                StepState.Current -> {
                    drawCircle(
                        color = colors.current,
                        style = Stroke(
                            width = properties.strokeWidth,
                        ),
                    )
                    drawCircle(
                        color = colors.current,
                        radius = size.width / properties.strokeRatio,
                    )
                }

                StepState.Coming -> {
                    drawCircle(
                        color = colors.coming,
                        style = Stroke(
                            width = properties.strokeWidth,
                        ),
                    )
                }
            }
        }
    }
}
