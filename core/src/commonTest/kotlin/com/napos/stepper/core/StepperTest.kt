package com.napos.stepper.core

import com.napos.stepper.core.steps.ProfileInformation
import com.napos.stepper.core.steps.civil.CivilInformation
import com.napos.stepper.core.steps.civil.CivilStep
import com.napos.stepper.core.steps.contact.Contact
import com.napos.stepper.core.steps.contact.ContactStep
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.*

class StepperTest {

    private val civil: CivilStep = CivilStep()
    private val contact: ContactStep = ContactStep()

    private val json: Json = Json {
        serializersModule = SerializersModule {
            polymorphic(StepData::class) {
                subclass(CivilInformation::class)
                subclass(Contact::class)
            }
        }
    }

    private lateinit var stepper: Stepper

    @BeforeTest
    fun setup() {
        stepper = Stepper.Builder()
            .setSerializationConfiguration(json)
            .addStep(civil)
            .addStep(contact)
            .build()
    }

    @Test
    fun check_that_addSteps_works() {
        val steps = listOf(civil, contact)
        val stepper = Stepper.Builder()
            .setSerializationConfiguration(json)
            .addSteps(steps)
            .build()

        assertTrue { this.stepper.currentIndex == stepper.currentIndex }
        assertTrue { this.stepper.size == stepper.size }
        assertTrue { this.stepper.getCurrent() == stepper.getCurrent() }
        this.stepper.next()
        stepper.next()
        assertTrue { this.stepper.currentIndex == stepper.currentIndex }
        assertTrue { this.stepper.getCurrent() == stepper.getCurrent() }
        this.stepper.next()
        stepper.next()
        assertTrue { this.stepper.currentIndex == stepper.currentIndex }
        assertTrue { this.stepper.getCurrent() == stepper.getCurrent() }
    }

    @Test
    fun check_that_stepper_is_ordered() {
        assertTrue { stepper.size == 2 }
        assertTrue { stepper.currentIndex == 0 }
        assertTrue { stepper.getCurrent() == civil }
        assertTrue { stepper.getCurrent().next == contact }
        stepper.next()
        assertTrue { stepper.currentIndex == 1 }
        assertTrue { stepper.getCurrent() == contact }
        assertTrue { stepper.getCurrent().previous == civil }
        assertNull(stepper.getCurrent().next)
        stepper.next()
        assertTrue { stepper.getCurrent() == contact }
    }

    @Test
    fun check_that_rollback_works() {
        assertTrue { stepper.getCurrent() == civil }
        assertTrue { stepper.getCurrent().next == contact }
        stepper.next()
        assertTrue { stepper.getCurrent() == contact }
        assertTrue { stepper.getCurrent().previous == civil }
        stepper.rollback()
        assertTrue { stepper.getCurrent() == civil }
        assertTrue { stepper.getCurrent().next == contact }
    }

    @Test
    fun check_that_filling_step_data_works() {
        civil.data = CivilInformation(
            firstname = "Test",
            lastname = "Test",
        )
        assertNotNull(civil.data)
        stepper.fill(civil.data!!)
        assertTrue { stepper.getCurrent().data == civil.data }
    }

    @Test
    fun check_that_filling_step_data_fails_for_inappropriate_step() {
        val contact = Contact(email = "some@email.com")
        assertFailsWith(IllegalArgumentException::class) {
            stepper.fill(contact)
        }
    }

    @Test
    fun check_that_aggregation_works() {
        // Civil
        civil.data = CivilInformation(
            firstname = "Test",
            lastname = "Test",
        )
        assertNotNull(civil.data)
        stepper.fill(civil.data!!)
        assertTrue { stepper.getCurrent().data == civil.data }
        stepper.next()
        // Contact
        contact.data = Contact(email = "some@email.com")
        assertNotNull(contact.data)
        stepper.fill(contact.data!!)
        assertTrue { stepper.getCurrent().data == contact.data }

        val profile = stepper.aggregate<ProfileInformation>()
        assertNotNull(profile)
        assertTrue { profile.civil == civil.data }
        assertTrue { profile.contact == contact.data }
    }
}