package com.napos.stepper.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer

/**
 * @param [root] The root i.e. first, step of the stepper to which the aggregation is needed
 * @param [json] The [Json] configuration that should be defined to make the aggregation possible based on a
 * polymorphic definition that has to be defined before the stepper is built.
 *
 * Example: Following the same example in [AggregationResult] documentation.
 * ```
 * // Serialization configuration definition
 * val json = Json {
 *      polymorphic(StepData::class) {
 *          subclass(CivilInformation::class)
 *          subclass(Contact::class)
 *      }
 * }
 *
 * // Pass it into the Stepper.Builder
 * val stepper = Stepper.Builder()
 *      // ... Some stepper building functions
 *      .setSerializationConfiguration(json)
 *      // ... Some other stepper building functions
 *      .build()
 * ```
 * @return A data class annotated with [AggregationResult] that represents the aggregated data collected throughout
 * the steps of the stepper. The result class should be defined explicitly at call site.
 * @see [AggregationResult]
 */
@OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
@AggregationResult
public inline fun <@AggregationResult reified R> aggregate(
    root: Milestone<*>,
    json: Json,
): R {
    // Enforce no class discriminator mode
    require(json.configuration.classDiscriminatorMode == ClassDiscriminatorMode.NONE) {
        "classDiscriminatorMode should be set to NONE"
    }
    val result = buildJsonObject {
        var current: Milestone<*>? = root
        while (current != null) {
            require (current.data != null) { "All steps should contain data before the aggregation attempt occurs" }
            val serializer = current.data!!::class.serializer()
            put(
                key = serializer.descriptor.serialName,
                element = json.encodeToJsonElement(current.data!!),
            )
            current = current.next
        }
    }
    return Json.decodeFromString(result.toString())
}