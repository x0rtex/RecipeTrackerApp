package com.x0rtex.recipetrackerapp

import com.x0rtex.recipetrackerapp.data.models.IngredientInput
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for IngredientInput conversion to IngredientEntity.
 */
class IngredientInputTest {

    @Test
    fun `toIngredientEntity converts valid input correctly`() {
        val input = IngredientInput(
            name = "Flour",
            amount = "200",
            unit = "g"
        )

        val entity = input.toIngredientEntity()

        assertEquals("Flour", entity.name)
        assertEquals(200.0, entity.amount!!, 0.01)
        assertEquals("g", entity.unit)
    }

    @Test
    fun `toIngredientEntity handles invalid amount`() {
        val input = IngredientInput(
            name = "Sugar",
            amount = "invalid",
            unit = "cups"
        )

        val entity = input.toIngredientEntity()

        assertEquals("Sugar", entity.name)
        assertNull(entity.amount)
        assertEquals("cups", entity.unit)
    }

    @Test
    fun `toIngredientEntity handles empty unit`() {
        val input = IngredientInput(
            name = "Eggs",
            amount = "2",
            unit = ""
        )

        val entity = input.toIngredientEntity()

        assertEquals("Eggs", entity.name)
        assertEquals(2.0, entity.amount!!, 0.01)
        assertNull(entity.unit)
    }
}