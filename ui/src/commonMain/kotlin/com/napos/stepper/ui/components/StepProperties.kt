package com.napos.stepper.ui.components

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
    public data object Default : StepColors(
        passed = Color.Red,
        current = Color.Cyan,
        coming = Color.Red,
    )
}


public val LocalStepperPropertiesProvider: ProvidableCompositionLocal<StepProperties> =
    compositionLocalOf {
        error("")
    }


public val LocalStepperColorsProvider: ProvidableCompositionLocal<StepColors> =
    compositionLocalOf {
        error("")
    }