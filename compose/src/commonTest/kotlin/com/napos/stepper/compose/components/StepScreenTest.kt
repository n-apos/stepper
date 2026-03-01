package com.napos.stepper.compose.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.compose.TestScreenProvider
import com.napos.stepper.compose.createRoadmap
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StepScreenTest {

    @Test
    fun should_display_screen_content() = runComposeUiTest {
        val roadmap = createRoadmap()
        val screen = TestScreenProvider.provide(roadmap.getCurrent())

        setContent {
            StepScreen(
                screen = screen
            )
        }

        onNodeWithText("Step 1 Title").assertExists()
        onNodeWithText("Content for Step 1").assertExists()
    }
}
