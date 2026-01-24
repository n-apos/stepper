package com.napos.stepper.example

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.AggregatedResult
import com.napos.stepper.example.steps.first.FirstData
import com.napos.stepper.example.steps.second.SecondData
import com.napos.stepper.example.steps.third.ThirdData
import com.napos.stepper.compose.components.Stepper
import com.napos.stepper.compose.screen.MilestoneScreenProvider


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

    Surface {
        Stepper(
            roadmap = roadmap,
            provider = provider,
            onSubmit = {
                println(roadmap.aggregate<AggregatedResult>())
            },
        )
    }
}