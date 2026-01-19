package com.napos.stepper.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public open class StepProperties(
    public val size: Dp,
    public val strokeRatio: Float,
) {

    public val strokeWidth: Float
        get() = (size.value / strokeRatio) * 0.75f

    public data object Default : StepProperties(
        size = 20.dp,
        strokeRatio = 3f,
    )
}


public open class StepColors(
    public val passed: Color,
    public val current: Color,
    public val coming: Color,
) {
    public object Default {
        @Composable
        public fun defaultColors(): StepColors = StepColors(
            passed = MaterialTheme.colorScheme.primary,
            current = MaterialTheme.colorScheme.secondary,
            coming = MaterialTheme.colorScheme.tertiary,
        )
    }
}


public val LocalStepProperties: ProvidableCompositionLocal<StepProperties> =
    compositionLocalOf {
        StepProperties.Default
    }


public val LocalStepColors: ProvidableCompositionLocal<StepColors> =
    compositionLocalOf {
        error("No StepColors provided")
    }
