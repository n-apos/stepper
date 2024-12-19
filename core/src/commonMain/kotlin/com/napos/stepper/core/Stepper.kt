package com.napos.stepper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.serialization.json.Json

class Stepper private constructor(
    @PublishedApi
    internal val root: Step,
    @PublishedApi
    internal val json: Json,
    val size: Int,
) {
    private var _current: MutableStateFlow<Step> = MutableStateFlow(value = root)

    val current: Flow<Step> = _current
        .buffer()

    fun getCurrent(): Step = _current.value

    val currentIndex: Int
        get() {
            var index = 0
            var cursor: Step? = root
            while (cursor != _current.value) {
                cursor = cursor?.next
                index++
            }
            return index
        }

    fun fill(data: StepData) {
        _current.value.fill(data)
    }

    fun next() {
        _current.value.next?.let { next ->
            _current.value = next
        }
    }

    fun rollback() {
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    inline fun <reified T> aggregate(): T =
        aggregate(root, json)

    class Builder {

        private lateinit var root: Step
        private lateinit var tip: Step

        private var size: Int = 0

        private lateinit var configuration: Json

        fun addStep(step: Step) =
            apply {
                if (!::root.isInitialized) {
                    root = step
                    tip = step
                } else {
                    step.previous = tip
                    tip.next = step
                    tip = step
                }
                size++
            }

        fun addSteps(steps: List<Step>) =
            apply { steps.forEach { addStep(it) } }

        fun setSerializationConfiguration(json: Json) =
            apply { configuration = json }

        fun build(): Stepper = Stepper(root, configuration, size)
    }
}