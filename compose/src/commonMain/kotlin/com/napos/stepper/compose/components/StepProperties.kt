package com.napos.stepper.compose.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines the visual properties of a step indicator in the stepper.
 *
 * @property size The diameter of the step indicator.
 * @property strokeRatio The ratio of the step's size to its stroke width. Used to calculate [strokeWidth].
 */
public open class StepProperties(
    public val size: Dp,
    public val strokeRatio: Float,
) {

    /**
     * The calculated width of the stroke for the step indicator, derived from [size] and [strokeRatio].
     */
    public val strokeWidth: Float
        get() = (size.value / strokeRatio) * 0.75f

    /**
     * Provides a default set of step properties.
     */
    public data object Default : StepProperties(
        size = 20.dp,
        strokeRatio = 3f,
    )
}

/**
 * Defines the colors used for the different states of a step indicator and its connecting link.
 *
 * @property passed The color for a step that has been completed.
 * @property current The color for the currently active step.
 * @property coming The color for a step that has not yet been reached.
 */
public open class StepColors(
    public val passed: Color,
    public val current: Color,
    public val coming: Color,
) {
    /**
     * Provides a default set of colors derived from the application's [MaterialTheme].
     */
    public object Default {
        /**
         * Creates a default [StepColors] instance using the primary, secondary, and tertiary colors
         * from the current [MaterialTheme].
         */
        @Composable
        public fun defaultColors(): StepColors = StepColors(
            passed = MaterialTheme.colorScheme.primary,
            current = MaterialTheme.colorScheme.secondary,
            coming = MaterialTheme.colorScheme.tertiary,
        )
    }
}

/**
 * A [ProvidableCompositionLocal] that provides [StepProperties] down the composition tree.
 *
 * This allows for overriding the default step properties at a higher level in the UI hierarchy.
 */
public val LocalStepProperties: ProvidableCompositionLocal<StepProperties> =
    compositionLocalOf {
        StepProperties.Default
    }

/**
 * A [ProvidableCompositionLocal] that provides [StepColors] down the composition tree.
 *
 * This allows for overriding the default step colors at a higher level in the UI hierarchy.
 * An error will be thrown if no [StepColors] are provided.
 */
public val LocalStepColors: ProvidableCompositionLocal<StepColors> =
    compositionLocalOf {
        error("No StepColors provided")
    }
