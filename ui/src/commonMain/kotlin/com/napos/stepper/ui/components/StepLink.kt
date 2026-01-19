package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
public fun StepLink(state: StepState) {
    val colors = LocalStepColors.current
    val color = when (state) {
        StepState.PASSED, StepState.CURRENT -> colors.passed
        StepState.COMING -> colors.coming
    }
    Canvas(
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .testTag("step_link"),
        onDraw = {
            drawLine(
                color = color,
                start = Offset(x = 0f, y = size.height / 2),
                end = Offset(x = size.width, y = size.height / 2),
                strokeWidth = 2f,
            )
        }
    )
}
