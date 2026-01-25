package com.napos.stepper.compose.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.compose.TestScreenProvider
import com.napos.stepper.compose.createRoadmap
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StepperTest {

    @Test
    fun initial_state_should_display_first_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        setContent {
            Stepper(
                roadmap = roadmap,
                provider = TestScreenProvider,
                onSubmit = {},
                nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } }
            )
        }

        onNodeWithText("Step 1 Title").assertExists()
        onNodeWithText("Content for Step 1").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertDoesNotExist()
        onNodeWithText("Submit").assertDoesNotExist()
    }

    @Test
    fun clicking_next_should_navigate_to_next_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        setContent {
            Stepper(
                roadmap = roadmap,
                provider = TestScreenProvider,
                onSubmit = {},
                nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } }
            )
        }

        onNodeWithText("Next").performClick()

        onNodeWithText("Step 2 Title").assertExists()
        onNodeWithText("Content for Step 2").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertExists()
        onNodeWithText("Submit").assertDoesNotExist()
    }

    @Test
    fun clicking_previous_should_navigate_to_previous_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        roadmap.next() // Start at step 2

        setContent {
            Stepper(
                roadmap = roadmap,
                provider = TestScreenProvider,
                onSubmit = {},
                nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } }
            )
        }

        onNodeWithText("Previous").performClick()

        onNodeWithText("Step 1 Title").assertExists()
        onNodeWithText("Content for Step 1").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertDoesNotExist()
        onNodeWithText("Submit").assertDoesNotExist()
    }

    @Test
    fun last_step_should_show_submit_button() = runComposeUiTest {
        val roadmap = createRoadmap()
        roadmap.next()
        roadmap.next()

        setContent {
            Stepper(
                roadmap = roadmap,
                provider = TestScreenProvider,
                onSubmit = {},
                nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } }
            )
        }

        onNodeWithText("Step 3 Title").assertExists()
        onNodeWithText("Content for Step 3").assertExists()
        onNodeWithText("Next").assertDoesNotExist()
        onNodeWithText("Previous").assertExists()
        onNodeWithText("Submit").assertExists()
    }
}
