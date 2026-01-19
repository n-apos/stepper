package com.napos.stepper.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.napos.stepper.core.Milestone
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class MilestoneScreenProviderTest {

    // Test implementations
    class TestMilestone(
        override var data: StepData?,
        override var previous: Milestone<*>?,
        override var next: Milestone<*>?
    ) : Milestone<StepData>()

    class TestViewModel : MilestoneViewModel()

    class TestScreen(
        override val milestone: TestMilestone,
        override val viewModel: TestViewModel,
    ) : MilestoneScreen<TestMilestone, TestViewModel>() {

        @Composable
        override fun title(): String = "Test"


        @Composable
        override fun render() {
            Text("Test Screen")
        }
    }

    class TestScreenProvider : MilestoneScreenProvider() {
        override fun provide(milestone: Milestone<*>): MilestoneScreen<*, *> {
            return TestScreen(TestMilestone(null, null, null), TestViewModel())
        }
    }

    @Test
    fun testMilestoneScreenProvider() = runComposeUiTest {
        val provider = TestScreenProvider()
        val screen = provider.provide(TestMilestone(null, null, null))

        setContent {
            screen.render()
        }

        onNodeWithText("Test Screen").assertExists()
    }
}
