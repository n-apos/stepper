package com.napos.stepper.core.steps.civil

import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("civil")
data class CivilInformation(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String,
): MilestoneData()
