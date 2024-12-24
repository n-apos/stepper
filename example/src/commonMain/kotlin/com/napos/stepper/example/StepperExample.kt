package com.napos.stepper.example

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.napos.stepper.core.Roadmap
import com.napos.stepper.example.steps.AggregatedResult
import com.napos.stepper.ui.components.StepColors
import com.napos.stepper.ui.components.StepProperties
import com.napos.stepper.ui.components.Stepper
import org.koin.compose.koinInject


@Composable
fun StepperExample() {
    val roadmap: Roadmap = koinInject()

    Surface {
        Stepper(
            roadmap = roadmap,
            properties = StepProperties(
                size = 40.dp,
                strokeRatio = 3f,
            ),
            colors = StepColors(
                passed = Color.Blue,
                current = Color.Black,
                coming = Color.Red,
            ),
            onSubmit = {
                println(roadmap.aggregate<AggregatedResult>())
            }
        )
    }
}