package com.napos.stepper.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
public fun StepButton(
    onClick: () -> Unit,
    text: String,
) {
    Button(
        onClick = onClick
    ) {
        Text(text = text)
    }
}