package com.napos.stepper.example.steps.third

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import com.napos.stepper.ui.screen.MilestoneScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ThirdScreen(
    override val milestone: ThirdMilestone
) : MilestoneScreen<ThirdMilestone, ThirdViewModel>(), KoinComponent {
    override val viewModel: ThirdViewModel by inject()

    @Composable
    override fun render() {
        var value by remember { mutableStateOf(milestone.data?.value ?: "") }
        milestone.data = ThirdData(
            value = value,
        )
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Third value") }
        )
    }
}