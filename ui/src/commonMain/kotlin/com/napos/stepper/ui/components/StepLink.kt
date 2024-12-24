package com.napos.stepper.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

@Composable
public fun PassedStepLink() {
    val properties = LocalStepperPropertiesProvider.current
    val colors = LocalStepperColorsProvider.current

    Canvas(
        modifier = Modifier
            .size(properties.size),
        onDraw = {

            val linkWidth = size.width * 0.8f
            val startX = size.width - linkWidth
            val endX = size.width - startX

            drawLine(
                start = Offset(x = startX, y = size.height / 2),
                end = Offset(x = endX, y = size.height / 2),
                strokeWidth = properties.strokeWidth,
                color = colors.passed,
            )
        },
    )
}

@Composable
public fun ComingStepLink() {
    val properties = LocalStepperPropertiesProvider.current
    val colors = LocalStepperColorsProvider.current

    Canvas(
        modifier = Modifier
            .size(properties.size),
        onDraw = {

            val linkWidth = size.width * 0.8f
            val startX = size.width - linkWidth
            val endX = size.width - startX

            drawLine(
                start = Offset(x = startX, y = size.height / 2),
                end = Offset(x = endX, y = size.height / 2),
                strokeWidth = properties.strokeWidth,
                color = colors.coming,
            )
        },
    )
}