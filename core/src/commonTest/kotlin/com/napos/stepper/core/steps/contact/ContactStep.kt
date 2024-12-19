package com.napos.stepper.core.steps.contact

import com.napos.stepper.core.Step
import com.napos.stepper.core.StepData

class ContactStep(
    override var data: Contact? = null,
    override var previous: Step? = null,
    override var next: Step? = null,
) : Step() {

    override fun fill(stepData: StepData) {
        data = stepData as? Contact
            ?: throw IllegalArgumentException("The submitted step data is not of type ${Contact::class}")
    }
}