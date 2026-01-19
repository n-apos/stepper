package com.napos.stepper.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.serialization.json.Json

public class Roadmap internal constructor(
    @PublishedApi
    internal val root: Milestone<*>,
    @PublishedApi
    internal val json: Json,
    public val size: Int,
) {

    public val milestones: List<Milestone<*>>

    private var _current: MutableStateFlow<Milestone<*>> = MutableStateFlow(value = root)

    public val current: Flow<Milestone<*>> = _current
        .buffer()

    public fun getCurrent(): Milestone<*> = _current.value

    public val currentIndex: Int
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

    public fun fill(data: MilestoneData) {
        _current.value.fill(data)
    }

    public fun next() {
        _current.value.next?.let { next ->
            _current.value = next
        }
    }

    public fun previous() {
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    public fun rollback() {
        _current.value.data = null
        _current.value.previous?.let { previous ->
            _current.value = previous
        }
    }

    public fun reset() {
        var cursor: Milestone<*>? = root
        while (cursor != null) {
            cursor.data = null
            cursor = cursor.next
        }
    }

    public inline fun <reified T> aggregate(): T =
        aggregate(root, json)

}


public class RoadmapBuilder @PublishedApi internal constructor() {

    /**
     * Construction specific fields
     */

    private lateinit var root: Milestone<*>
    private lateinit var tip: Milestone<*>
    private var size: Int = 0


    /**
     * Builder specific fields, exposed publicly
     */
    public lateinit var configuration: Json
    public val milestones: MutableList<Milestone<*>> = mutableListOf()


    /**
     * Builder APIs
     */
    internal fun add(milestone: Milestone<*>) {
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

    internal fun create() {
        milestones.forEach { milestone ->
            add(milestone)
        }
    }


    /**
     * Builder performer action
     */
    internal fun build(): Roadmap {
        require(::configuration.isInitialized) {
            error("${::configuration.name} field should be initialized")
        }
        create()
        return Roadmap(root, configuration, size)
    }

}

public fun Roadmap(block: RoadmapBuilder.() -> Unit): Roadmap {
    val builder = RoadmapBuilder()
    builder.block()
    return builder.build()
}