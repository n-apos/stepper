package com.napos.stepper.example.steps.second

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import com.napos.stepper.example.Res
import com.napos.stepper.example.stepper_second_screen_title
import com.napos.stepper.compose.screen.MilestoneScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SecondScreen(
    override val milestone: SecondMilestone,
) : MilestoneScreen<SecondMilestone, SecondViewModel>(), KoinComponent {

    override val viewModel: SecondViewModel by inject()

    @Composable
    override fun title(): String = stringResource(Res.string.stepper_second_screen_title)

    @Composable
    override fun render() {
        var value by remember { mutableStateOf(milestone.data?.value ?: "") }
        milestone.data = SecondData(
            value = value,
        )
        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Second value") }
        )
    }

}