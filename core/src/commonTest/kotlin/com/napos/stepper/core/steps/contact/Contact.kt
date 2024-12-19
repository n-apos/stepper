package com.napos.stepper.core.steps.contact

import com.napos.stepper.core.StepData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("contact")
data class Contact(
    @SerialName("email")
    val email: String,
) : StepData()