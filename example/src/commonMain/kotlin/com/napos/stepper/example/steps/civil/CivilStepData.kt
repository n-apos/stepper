package com.napos.stepper.example.steps.civil

import com.napos.stepper.core.StepContent
import com.napos.stepper.core.StepData

@StepContent("civil")
data class CivilStepData(
    val firstname: String,
    val lastname: String,
): StepData()
