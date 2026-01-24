package com.napos.stepper.compose.components

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StepLinkTest {

    @Test
    fun testPassedStepLink() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(
                LocalStepColors provides StepColors.Default.defaultColors(),
                LocalStepProperties provides StepProperties.Default,
            ) {
                StepLink(state = StepState.Passed)
            }
        }
        onNodeWithTag("step_link").assertExists()
    }

    @Test
    fun testComingStepLink() = runComposeUiTest {
        setContent {
            CompositionLocalProvider(
                LocalStepColors provides StepColors.Default.defaultColors(),
                LocalStepProperties provides StepProperties.Default,
            ) {
                StepLink(state = StepState.Coming)
            }
        }
        onNodeWithTag("step_link").assertExists()
    }
}
