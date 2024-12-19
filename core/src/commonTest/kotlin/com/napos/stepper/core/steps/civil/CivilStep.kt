package com.napos.stepper.core.steps.civil

import com.napos.stepper.core.Step
import com.napos.stepper.core.StepData

class CivilStep(
    override var data: CivilInformation? = null,
    override var previous: Step? = null,
    override var next: Step? = null,
) : Step() {

    override fun fill(stepData: StepData) {
        data = stepData as? CivilInformation
            ?: throw IllegalArgumentException("The submitted step data is not of type ${CivilInformation::class}")
    }
}
