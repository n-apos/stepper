package com.napos.stepper.example.steps

import com.napos.stepper.core.AggregationResult
import com.napos.stepper.core.StepContent
import com.napos.stepper.example.steps.civil.CivilStepData
import com.napos.stepper.example.steps.contact.ContactStepData

@AggregationResult
data class ProfileInformation(
    @StepContent("civil")
    val civil: CivilStepData,
    @StepContent("contact")
    val contact: ContactStepData,
)
