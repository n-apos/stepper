package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag

public enum class StepState {
    PASSED,
    CURRENT,
    COMING
}

@Composable
public fun Step(state: StepState) {
    val properties = LocalStepProperties.current
    val colors = LocalStepColors.current

    Canvas(
        modifier = Modifier
            .size(properties.size)
            .testTag("step"),
        onDraw = {
            when (state) {
                StepState.PASSED -> {
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

                StepState.CURRENT -> {
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

                StepState.COMING -> {
                    drawCircle(
                        color = colors.coming,
                        style = Stroke(
                            width = properties.strokeWidth,
                        ),
                    )
                }
            }
        }
    )
}
