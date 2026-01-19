package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

public enum class StepState {
    Passed,
    Current,
    Coming
}

public enum class TitlePosition {
    Top,
    Bottom,
    ;
}

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
