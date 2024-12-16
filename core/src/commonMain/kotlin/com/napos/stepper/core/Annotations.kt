package com.napos.stepper.core

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MetaSerializable


@OptIn(ExperimentalSerializationApi::class)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER,
)
@MetaSerializable
annotation class StepContent(
    val name: String = "",
)

@OptIn(ExperimentalSerializationApi::class)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE_PARAMETER,
)
@MetaSerializable
annotation class AggregationResult