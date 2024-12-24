package com.napos.stepper.example.steps.first

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import com.napos.stepper.ui.screen.MilestoneScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public class FirstScreen(
    override val milestone: FirstMilestone,
) : MilestoneScreen<FirstMilestone, FirstViewModel>(), KoinComponent {

    override val viewModel: FirstViewModel by inject()

    @Composable
    override fun render() {
        var value by remember { mutableStateOf(milestone.data?.value ?: "") }
        milestone.data = FirstData(
            value = value,
        )
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("First value") }
        )
    }
}
