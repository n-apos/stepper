package com.napos.stepper.compose.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.napos.stepper.compose.TestScreenProvider
import com.napos.stepper.compose.createRoadmap
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class StepperTest {

    @Test
    fun initial_state_should_display_preview_screen() = runComposeUiTest {
        val roadmap = createRoadmap()
        var startText = ""

        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) {
                Stepper(
                    roadmap = roadmap,
                    provider = TestScreenProvider,
                    onSubmit = {},
                    components = StepperComponents(startButton = { text, onClick ->
                        Button(onClick = onClick) { Text(text) }
                        startText = text
                    }),
                )
            }
        }

        onNodeWithText("Preview for Step 1").assertExists()
        onNodeWithText(startText).assertExists()
    }

    @Test
    fun clicking_start_should_navigate_to_first_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        var startText = ""

        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) {
                Stepper(
                    roadmap = roadmap,
                    provider = TestScreenProvider,
                    onSubmit = {},
                    components = StepperComponents(
                        startButton = { text, onClick ->
                            Button(onClick = onClick) { Text(text) }
                            startText = text
                        },
                        nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                    ),
                )
            }
        }

        onNodeWithText(startText).performClick()

        onNodeWithText("Content for Step 1").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertDoesNotExist()
        assertEquals(0, roadmap.currentIndex)
    }

    @Test
    fun clicking_next_should_navigate_to_next_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        var startText = ""


        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) {
                Stepper(
                    roadmap = roadmap,
                    provider = TestScreenProvider,
                    onSubmit = {},
                    components = StepperComponents(
                        startButton = { text, onClick ->
                            Button(onClick = onClick) { Text(text) }
                            startText = text
                        },
                        nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                        previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    ),
                )
            }
        }

        onNodeWithText(startText).performClick()
        onNodeWithText("Next").performClick()

        onNodeWithText("Content for Step 2").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertExists()
        assertEquals(1, roadmap.currentIndex)
    }

    @Test
    fun clicking_previous_should_navigate_to_previous_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        var startText = ""

        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) {
                Stepper(
                    roadmap = roadmap,
                    provider = TestScreenProvider,
                    onSubmit = {},
                    components = StepperComponents(
                        startButton = { text, onClick ->
                            Button(onClick = onClick) {
                                Text(text)
                                startText = text
                            }
                        },
                        nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                        previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    ),
                )
            }
        }

        onNodeWithText(startText).performClick()
        onNodeWithText("Next").performClick() // Go to step 2

        onNodeWithText("Previous").performClick() // Go back to step 1

        onNodeWithText("Content for Step 1").assertExists()
        onNodeWithText("Next").assertExists()
        onNodeWithText("Previous").assertDoesNotExist()
        assertEquals(0, roadmap.currentIndex)
    }

    @Test
    fun clicking_submit_should_call_onSubmit_and_return_to_preview() = runComposeUiTest {
        val roadmap = createRoadmap()
        var submitted = false
        var startText = ""


        setContent {
            CompositionLocalProvider(LocalLifecycleOwner provides TestLifecycleOwner()) {
                Stepper(
                    roadmap = roadmap,
                    provider = TestScreenProvider,
                    onSubmit = { submitted = true },
                    components = StepperComponents(
                        startButton = { text, onClick ->
                            Button(onClick = onClick) { Text(text) }
                            startText = text
                        },
                        nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                        previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                        submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } },
                    ),
                )
            }
        }

        // Navigate to the last step
        onNodeWithText(startText).performClick()
        onNodeWithText("Next").performClick()
        onNodeWithText("Next").performClick()

        onNodeWithText("Content for Step 3").assertExists()
        onNodeWithText("Submit").assertExists()

        onNodeWithText("Submit").performClick()

        assertEquals(true, submitted)

        // Check that we are back on the preview screen
        onNodeWithText("Preview for Step 1").assertExists()
        onNodeWithText(startText).assertExists()

        // Check that roadmap is reset
        assertEquals(0, roadmap.currentIndex)
    }
}

private class TestLifecycleOwner : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}
