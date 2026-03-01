package com.napos.stepper.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.napos.stepper.compose.screen.MilestoneScreenProvider
import com.napos.stepper.core.Roadmap

@Composable
public fun PreviewScreen(
    roadmap: Roadmap,
    provider: MilestoneScreenProvider,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.verticalScroll(rememberScrollState())
            .width(IntrinsicSize.Max)
    ) {
        val screens = roadmap.milestones.map { provider.provide(it) }
        screens.forEach { screen ->
            Text(screen.title())

            screen.preview()
        }
    }

}