package com.napos.stepper.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@AggregationResult
inline fun <@AggregationResult reified R> aggregate(
    root: Step,
    transformer: (Json, Any) -> JsonElement,
): R {
    val res = buildJsonObject {
        var current: Step? = root
        while (current != null) {
            if (current.data == null) throw IllegalStateException("All steps should contain data before the aggregation attempt occurs")
            val serializer = current.data!!::class.serializer()
            val serialized = transformer(Json, current.data!!)
            put(
                key = serializer.descriptor
                    .annotations
                    .filterIsInstance<StepContent>()
                    .first().name,
                element = Json.encodeToJsonElement(serialized),
            )
            current = current.next
        }
    }
    return Json.decodeFromString(res.toString())
}