package com.napos.stepper.example

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.ExampleMilestoneProvider
import com.napos.stepper.example.steps.first.FirstMilestone
import com.napos.stepper.example.steps.second.SecondMilestone
import com.napos.stepper.example.steps.third.ThirdMilestone
import kotlinx.serialization.json.Json
import kotlin.random.Random


val roadmap = Roadmap {
    configuration = Json
    milestones += listOf(FirstMilestone(), SecondMilestone(), ThirdMilestone())
}


@Preview
@Composable
fun StepperPreview() {
    val provider = ExampleMilestoneProvider()
    StepperExample(
        roadmap = roadmap,
        provider = provider,
    )
}