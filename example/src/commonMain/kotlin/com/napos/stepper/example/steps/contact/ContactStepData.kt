package com.napos.stepper.example.steps.contact

import com.napos.stepper.core.StepContent
import com.napos.stepper.core.StepData

@StepContent("contact")
data class ContactStepData(
    val email: String,
) : StepData()