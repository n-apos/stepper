package com.napos.stepper.core

abstract class Step {
    abstract val data: StepData?
    abstract var previous: Step?
    abstract var next: Step?

    @Throws(IllegalArgumentException::class)
    abstract fun fill(stepData: StepData)
}