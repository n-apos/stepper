package com.napos.stepper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class Stepper private constructor(
    @PublishedApi
    internal val root: Step,
    @PublishedApi
    internal val contentTransformer: (Json, Any) -> JsonElement,
    val size: Int,
) {
    private var _current: MutableStateFlow<Step> = MutableStateFlow(value = root)

    val current: Flow<Step> = _current
        .buffer()

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
        aggregate(root, contentTransformer)

    @Deprecated("Temporary function", level = DeprecationLevel.WARNING)
    fun printCurrentState() {
        println("==================================")
        println("|| Step\t\t: ${_current.value::class.simpleName}\t\t||")
        println("|| Index\t: ${currentIndex + 1}/$size\t\t\t||")
        println("|| Data\t\t: ${_current.value.data}\t||")
        println("|| Next\t\t: ${_current.value.next?.let { it::class.simpleName }}\t\t||")
        println("|| Previous\t: ${_current.value.previous?.let { it::class.simpleName }}\t\t\t||")
        println("==================================")
    }

    class Builder {

        private lateinit var root: Step
        private lateinit var tip: Step

        private var size: Int = 0

        private lateinit var contentTransformer: (Json, Any) -> JsonElement

        @Suppress(
            "Unused",
            "MemberVisibilityCanBePrivate",
        )
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

        @Suppress(
            "Unused",
            "MemberVisibilityCanBePrivate"
        )
        fun addSteps(steps: List<Step>) =
            apply { steps.forEach { addStep(it) } }

        fun initContentTransformer(transformer: (Json, Any) -> JsonElement) =
            apply {
                contentTransformer = transformer
            }

        fun build(): Stepper = Stepper(root, contentTransformer, size)
    }
}