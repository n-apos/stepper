package com.napos.stepper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.serialization.json.Json

class Roadmap private constructor(
    @PublishedApi
    internal val root: Milestone<*>,
    @PublishedApi
    internal val json: Json,
    val size: Int,
) {

    val milestones: List<Milestone<*>>

    private var _current: MutableStateFlow<Milestone<*>> = MutableStateFlow(value = root)

    val current: Flow<Milestone<*>> = _current
        .buffer()

    fun getCurrent(): Milestone<*> = _current.value

    val currentIndex: Int
        get() {
            var index = 0
            var cursor: Milestone<*>? = root
            while (cursor != _current.value) {
                cursor = cursor?.next
                index++
            }
            return index
        }

    init {
        val milestones: MutableList<Milestone<*>> = mutableListOf()
        var current: Milestone<*>? = root
        while (current != null) {
            milestones.add(current)
            current = current.next
        }
        this.milestones = milestones
    }

    fun fill(data: MilestoneData) {
        _current.value.fill(data)
    }

    fun next() {
        _current.value.next?.let { next ->
            _current.value = next
        }
    }

    fun previous() {
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    fun rollback() {
        _current.value.data = null
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    fun reset() {
        var cursor: Milestone<*>? = root
        while (cursor != null) {
            cursor.data = null
            cursor = cursor.next
        }
    }

    inline fun <reified T> aggregate(): T =
        aggregate(root, json)


    class Builder {

        private lateinit var root: Milestone<*>
        private lateinit var tip: Milestone<*>

        private var size: Int = 0

        private lateinit var configuration: Json

        fun addMilestone(milestone: Milestone<*>) =
            apply {
                if (!::root.isInitialized) {
                    root = milestone
                    tip = milestone
                } else {
                    milestone.previous = tip
                    tip.next = milestone
                    tip = milestone
                }
                size++
            }

        fun addMilestones(milestones: List<Milestone<*>>) =
            apply { milestones.forEach { addMilestone(it) } }

        fun setSerializationConfiguration(json: Json) =
            apply { configuration = json }

        fun build(): Roadmap = Roadmap(root, configuration, size)
    }
}