package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
internal fun PassedStep() {
    val properties = LocalStepperPropertiesProvider.current
    val colors = LocalStepperColorsProvider.current

    Canvas(
        modifier = Modifier
            .size(properties.size),
        onDraw = {
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
    )
}

@Composable
internal fun CurrentStep() {
    val properties = LocalStepperPropertiesProvider.current
    val colors = LocalStepperColorsProvider.current

    Canvas(
        modifier = Modifier
            .size(properties.size),
        onDraw = {
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
    )
}

@Composable
internal fun ComingStep() {
    val properties = LocalStepperPropertiesProvider.current
    val colors = LocalStepperColorsProvider.current

    Canvas(
        modifier = Modifier
            .size(properties.size),
        onDraw = {
            drawCircle(
                color = colors.coming,
                style = Stroke(
                    width = properties.strokeWidth,
                ),
            )
        }
    )
}