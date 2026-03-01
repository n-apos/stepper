package com.napos.stepper.compose.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.compose.TestScreenProvider
import com.napos.stepper.compose.createRoadmap
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PreviewScreenTest {

    @Test
    fun should_display_all_milestone_previews() = runComposeUiTest {
        val roadmap = createRoadmap()
        setContent {
            PreviewScreen(
                roadmap = roadmap,
                provider = TestScreenProvider
            )
        }

        onNodeWithText("Step 1 Title").assertExists()
        onNodeWithText("Preview for Step 1").assertExists()

        onNodeWithText("Step 2 Title").assertExists()
        onNodeWithText("Preview for Step 2").assertExists()

        onNodeWithText("Step 3 Title").assertExists()
        onNodeWithText("Preview for Step 3").assertExists()
    }
}
