package com.napos.stepper.example.steps.second

import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("second")
public data class SecondData(
    val value: String,
): MilestoneData()
