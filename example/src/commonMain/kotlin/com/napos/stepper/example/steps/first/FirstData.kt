package com.napos.stepper.example.steps.first

import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("first")
data class FirstData(
    @SerialName("value")
    val value: String,
): MilestoneData()
