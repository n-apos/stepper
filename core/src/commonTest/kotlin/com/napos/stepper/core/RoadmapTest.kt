package com.napos.stepper.core

import com.napos.stepper.core.steps.ProfileInformation
import com.napos.stepper.core.steps.civil.CivilInformation
import com.napos.stepper.core.steps.civil.CivilMilestone
import com.napos.stepper.core.steps.contact.Contact
import com.napos.stepper.core.steps.contact.ContactMilestone
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.ClassDiscriminatorMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.test.*

class RoadmapTest {

    private val civil: CivilMilestone = CivilMilestone()
    private val contact: ContactMilestone = ContactMilestone()

    @OptIn(ExperimentalSerializationApi::class)
    private val json: Json = Json {
        classDiscriminatorMode = ClassDiscriminatorMode.NONE
        serializersModule = SerializersModule {
            polymorphic(MilestoneData::class) {
                subclass(CivilInformation::class)
                subclass(Contact::class)
            }
        }
    }

    private lateinit var roadmap: Roadmap

    @BeforeTest
    fun setup() {
        roadmap = Roadmap {
            configuration = json
            milestones += listOf(civil, contact)
        }
    }

    @Test
    fun check_that_addSteps_works() {
        val steps = listOf(civil, contact)
        val roadmap = Roadmap {
            configuration = json
            milestones += steps
        }

        assertTrue { this.roadmap.currentIndex == roadmap.currentIndex }
        assertTrue { this.roadmap.size == roadmap.size }
        assertTrue { this.roadmap.getCurrent() == roadmap.getCurrent() }
        this.roadmap.next()
        roadmap.next()
        assertTrue { this.roadmap.currentIndex == roadmap.currentIndex }
        assertTrue { this.roadmap.getCurrent() == roadmap.getCurrent() }
        this.roadmap.next()
        roadmap.next()
        assertTrue { this.roadmap.currentIndex == roadmap.currentIndex }
        assertTrue { this.roadmap.getCurrent() == roadmap.getCurrent() }
    }

    @Test
    fun check_that_stepper_is_ordered() {
        assertTrue { roadmap.size == 2 }
        assertTrue { roadmap.currentIndex == 0 }
        assertTrue { roadmap.getCurrent() == civil }
        assertTrue { roadmap.getCurrent().next == contact }
        roadmap.next()
        assertTrue { roadmap.currentIndex == 1 }
        assertTrue { roadmap.getCurrent() == contact }
        assertTrue { roadmap.getCurrent().previous == civil }
        assertNull(roadmap.getCurrent().next)
        roadmap.next()
        assertTrue { roadmap.getCurrent() == contact }
    }

    @Test
    fun check_that_previous_works() {
        assertTrue { roadmap.getCurrent() == civil }
        assertTrue { roadmap.getCurrent().next == contact }
        roadmap.next()
        assertTrue { roadmap.getCurrent() == contact }
        assertTrue { roadmap.getCurrent().previous == civil }
        roadmap.previous()
        assertTrue { roadmap.getCurrent() == civil }
        assertTrue { roadmap.getCurrent().next == contact }
    }

    @Test
    fun check_that_filling_step_data_works() {
        civil.data = CivilInformation(
            firstname = "Test",
            lastname = "Test",
        )
        assertNotNull(civil.data)
        roadmap.fill(civil.data!!)
        assertTrue { roadmap.getCurrent().data == civil.data }
    }

    @Test
    fun check_that_filling_step_data_fails_for_inappropriate_step() {
        val contact = Contact(email = "some@email.com")
        assertFailsWith(ClassCastException::class) {
            roadmap.fill(contact)
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
        roadmap.fill(civil.data!!)
        assertTrue { roadmap.getCurrent().data == civil.data }
        roadmap.next()
        // Contact
        contact.data = Contact(email = "some@email.com")
        assertNotNull(contact.data)
        roadmap.fill(contact.data!!)
        assertTrue { roadmap.getCurrent().data == contact.data }

        val profile = roadmap.aggregate<ProfileInformation>()
        assertNotNull(profile)
        assertTrue { profile.civil == civil.data }
        assertTrue { profile.contact == contact.data }
    }
}