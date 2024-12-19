package com.napos.stepper.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MetaSerializable

/**
 * An annotation that helps aggregate step data that has been collected throughout the stepper.
 * The following example shows the aimed usage :
 * ```
 * // Steps data classes
 * @Serializable
 * data class CivilInformation(
 *      @SerialName("firstname")
 *      val firstname: String,
 *      @SerialName("lastname")
 *      val lastname: String,
 * )
 *
 * @Serializable
 * data class Contact(
 *      @SerialName("email")
 *      val email: String,
 * )
 *
 * // Aggregated data
 * @AggregationResult
 * data class ProfileInformation(
 *      @SerialName("civil")
 *      val civil: CivilInformation,
 *      @SerialName("contact")
 *      val contact: Contact,
 * )
 * ```
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE_PARAMETER,
)
@MetaSerializable
annotation class AggregationResult