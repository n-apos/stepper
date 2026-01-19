package com.napos.stepper.example.steps.third

import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("third")
data class ThirdData(
    @SerialName("value")
    val value: String,
): MilestoneData()