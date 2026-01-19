package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

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
                start = Offset(x = -size.height, y = size.height / 2),
                end = Offset(x = size.width * 2, y = size.height / 2),
                strokeWidth = properties.strokeWidth,
            )
        }
    )
}
