package com.napos.stepper.example.steps.civil

import com.napos.stepper.core.Step
import com.napos.stepper.core.StepData

class CivilStep(
    override var data: CivilStepData? = null,
    override var previous: Step? = null,
    override var next: Step? = null,
) : Step() {

    override fun fill(stepData: StepData) {
        data = stepData as? CivilStepData
            ?: throw IllegalArgumentException("The submitted step data is not of type ${CivilStepData::class}")
    }
}
