package com.napos.stepper.example.steps.contact

import com.napos.stepper.core.Step
import com.napos.stepper.core.StepData

class ContactStep(
    override var data: ContactStepData? = null,
    override var previous: Step? = null,
    override var next: Step? = null,
) : Step() {

    override fun fill(stepData: StepData) {
        data = stepData as? ContactStepData
            ?: throw IllegalArgumentException("The submitted step data is not of type ${ContactStepData::class}")
    }
}