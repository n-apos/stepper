package com.napos.stepper.compose.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.compose.StepData
import com.napos.stepper.compose.TestMilestone
import com.napos.stepper.compose.TestScreenProvider
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class MilestoneScreenProviderTest {

    @Test
    fun test_milestone_screen_provider() = runComposeUiTest {
        val screen = TestScreenProvider.provide(TestMilestone(StepData("Step 0"), null, null))

        setContent {
            screen.render()
        }

        onNodeWithText("Test Screen").assertExists()
    }
}
