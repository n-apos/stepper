package com.napos.stepper.example

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.napos.stepper.example.steps.ExampleMilestoneProvider
import com.napos.stepper.example.steps.ExampleRoadmap


@Preview
@Composable
fun StepperPreview() {
    StepperExample(
        roadmap = ExampleRoadmap,
        provider = ExampleMilestoneProvider,
    )
}