package com.napos.stepper.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.napos.stepper.compose.screen.MilestoneScreen
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.compose.screen.MilestoneViewModel
import com.napos.stepper.core.Milestone
import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.Serializable

@Serializable
data class StepData(
    val value: String,
) : MilestoneData()


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
    override fun title(): String = "${milestone.data?.value} Title"


    @Composable
    override fun render() {
        Text("Test Screen")
        Text("Content for ${milestone.data?.value}")
    }

    @Composable
    override fun preview() {
        Text("Preview for ${milestone.data?.value}")
    }
}

val TestScreenProvider = MilestoneScreenProvider { milestone ->
    TestScreen(TestMilestone(milestone.data as StepData, null, null), TestViewModel())
}

