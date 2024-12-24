package com.napos.stepper.example.steps

import com.napos.stepper.core.AggregationResult
import com.napos.stepper.example.steps.first.FirstData
import com.napos.stepper.example.steps.second.SecondData
import com.napos.stepper.example.steps.third.ThirdData
import kotlinx.serialization.SerialName


@AggregationResult
internal data class AggregatedResult(
    @SerialName("first")
    val first: FirstData,
    @SerialName("second")
    val second: SecondData,
    @SerialName("third")
    val third: ThirdData,
)
