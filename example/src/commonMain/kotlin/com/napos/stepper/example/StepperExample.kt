package com.napos.stepper.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.napos.stepper.compose.components.Stepper
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.AggregatedResult
import com.napos.stepper.example.steps.first.FirstData
import com.napos.stepper.example.steps.second.SecondData
import com.napos.stepper.example.steps.third.ThirdData


@Composable
fun StepperExample(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
) {
    val result = AggregatedResult(
        first = FirstData("first"),
        second = SecondData("second"),
        third = ThirdData("third"),
    )
    roadmap.milestones[0].fill(result.first)
    roadmap.milestones[1].fill(result.second)
    roadmap.milestones[2].fill(result.third)

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Stepper(
            roadmap = roadmap,
            provider = provider,
            onSubmit = {
                println(roadmap.aggregate<AggregatedResult>())
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}