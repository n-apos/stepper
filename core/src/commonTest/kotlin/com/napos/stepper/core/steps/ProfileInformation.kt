package com.napos.stepper.core.steps

import com.napos.stepper.core.AggregationResult
import com.napos.stepper.core.steps.civil.CivilInformation
import com.napos.stepper.core.steps.contact.Contact
import kotlinx.serialization.SerialName

@AggregationResult
data class ProfileInformation(
    @SerialName("civil")
    val civil: CivilInformation,
    @SerialName("contact")
    val contact: Contact,
)
