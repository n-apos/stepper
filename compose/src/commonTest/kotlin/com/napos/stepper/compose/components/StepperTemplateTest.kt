package com.napos.stepper.compose.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.compose.StepData
import com.napos.stepper.compose.TestScreenProvider
import com.napos.stepper.compose.createRoadmap
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class StepperTemplateTest {

    @Test
    fun initial_state_should_display_first_step() = runComposeUiTest {
        val roadmap = createRoadmap()
        setContent {
            StepperTemplate(
                roadmap = roadmap,
                provider = TestScreenProvider,
                type = StepperContentType.Step,
                onStart = {},
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                components = StepperComponents(
                    startButton = { text, onClick -> Button(onClick = onClick) { Text(text) } },
                    nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                    previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } },
                )
            ) {
                Text("Screen content")
            }
        }

        onNodeWithText("Screen content").assertExists()
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
            StepperTemplate(
                roadmap = roadmap,
                provider = TestScreenProvider,
                type = StepperContentType.Step,
                onStart = {},
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                components = StepperComponents(
                    startButton = { text, onClick -> Button(onClick = onClick) { Text(text) } },
                    nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                    previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } }
                )
            ) {
                Text("Screen content")
            }
        }

        onNodeWithText("Screen content").assertExists()
        onNodeWithText("Next").assertDoesNotExist()
        onNodeWithText("Previous").assertExists()
        onNodeWithText("Submit").assertExists()
    }

    @Test
    fun preview_state_should_display_start_button() = runComposeUiTest {
        val roadmap = createRoadmap()
        var startButtonText = ""
        setContent {
            StepperTemplate(
                roadmap = roadmap,
                provider = TestScreenProvider,
                type = StepperContentType.Preview,
                onStart = {},
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                components = StepperComponents(
                    startButton = { text, onClick ->
                        Button(onClick = onClick) { Text(text) }
                        startButtonText = text
                    },
                    nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                    previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } },
                )
            ) {
                Text("Preview")
            }
        }

        onNodeWithText("Preview").assertExists()
        onNodeWithText(startButtonText).assertExists()
        onNodeWithText("Next").assertDoesNotExist()
        onNodeWithText("Previous").assertDoesNotExist()
        onNodeWithText("Submit").assertDoesNotExist()
    }

    @Test
    fun preview_state_should_display_edit_button() = runComposeUiTest {
        val roadmap = createRoadmap()
        val data = StepData("Value")
        roadmap.fill(data)
        setContent {
            StepperTemplate(
                roadmap = roadmap,
                provider = TestScreenProvider,
                type = StepperContentType.Preview,
                onStart = {},
                onNext = {},
                onPrevious = {},
                onSubmit = {},
                components = StepperComponents(
                    startButton = { text, onClick -> Button(onClick = onClick) { Text(text) } },
                    nextButton = { onClick -> Button(onClick = onClick) { Text("Next") } },
                    previousButton = { onClick -> Button(onClick = onClick) { Text("Previous") } },
                    submitButton = { onClick -> Button(onClick = onClick) { Text("Submit") } },
                )
            ) {
                Text(roadmap.getCurrent().data.toString())
            }
        }

        onNodeWithText(data.toString()).assertExists()
        onNodeWithText("Edit").assertExists()
        onNodeWithText("Start").assertDoesNotExist()
        onNodeWithText("Next").assertDoesNotExist()
        onNodeWithText("Previous").assertDoesNotExist()
        onNodeWithText("Submit").assertDoesNotExist()
    }
}
