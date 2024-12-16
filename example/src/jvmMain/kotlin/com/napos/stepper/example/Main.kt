package com.napos.stepper.example

import com.napos.stepper.core.Stepper
import com.napos.stepper.example.steps.ProfileInformation
import com.napos.stepper.example.steps.civil.CivilStep
import com.napos.stepper.example.steps.civil.CivilStepData
import com.napos.stepper.example.steps.contact.ContactStep
import com.napos.stepper.example.steps.contact.ContactStepData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

private val transformer: (Json, Any) -> JsonElement = { json, obj ->
    when (obj) {
        is CivilStepData -> json.encodeToJsonElement(obj)
        is ContactStepData -> json.encodeToJsonElement(obj)
        else -> throw IllegalArgumentException("")
    }
}

fun main() = runBlocking {

    val civil = CivilStep()
    val contact = ContactStep()

    val stepper = Stepper.Builder()
        .initContentTransformer(transformer)
        .addStep(civil)
        .addStep(contact)
        .build()

    val job = launch {
        stepper.current
            .collect {
                stepper.printCurrentState()
            }
    }
    val civilData = CivilStepData(
        firstname = "Khalil",
        lastname = "Hamani"
    )
    val contactData = ContactStepData(
        email = "khalil.hamani@example.com",
    )

    delay(2000)
    stepper.fill(civilData)
    stepper.next()
    delay(2000)
    stepper.fill(contactData)
    stepper.next()
    delay(2000)
    println(stepper.aggregate<ProfileInformation>())
    job.cancel()
}