package com.napos.stepper.core.steps.contact

import com.napos.stepper.core.MilestoneData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("contact")
data class Contact(
    @SerialName("email")
    val email: String,
) : MilestoneData()