package com.napos.stepper.ui.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class StepButtonTest {

    @Test
    fun testStepButton() = runComposeUiTest {
        var clicked = false
        setContent {
            StepButton(text = "Click me", onClick = { clicked = true })
        }

        onNodeWithTag("step_button").assertExists()
        onNodeWithTag("step_button").performClick()

        assertTrue(clicked)
    }
}
