package com.napos.stepper.example

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.AggregatedResult
import com.napos.stepper.ui.components.Stepper
import com.napos.stepper.ui.screen.MilestoneScreenProvider
import org.koin.compose.koinInject


@Composable
fun StepperExample() {
    val roadmap: Roadmap = koinInject()
    val provider: MilestoneScreenProvider = koinInject()

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