package com.napos.stepper.ui.components

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StepTest {

    @Test
    fun testPassedStep() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(
                LocalStepColors provides StepColors.Default.defaultColors(),
                LocalStepProperties provides StepProperties.Default,
            ) {
                Step(state = StepState.PASSED)
            }
        }
        onNodeWithTag("step").assertExists()
    }

    @Test
    fun testCurrentStep() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(
                LocalStepColors provides StepColors.Default.defaultColors(),
                LocalStepProperties provides StepProperties.Default,
            ) {
                Step(state = StepState.CURRENT)
            }
        }
        onNodeWithTag("step").assertExists()
    }

    @Test
    fun testComingStep() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(
                LocalStepColors provides StepColors.Default.defaultColors(),
                LocalStepProperties provides StepProperties.Default,
            ) {
                Step(state = StepState.COMING)
            }
        }
        onNodeWithTag("step").assertExists()
    }
}
