package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.TemplateModel
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class TemplateEntityTest {
    @Test
    fun `map TemplateEntity to TemplateModel`() {
        /* Given */
        val templateEntity = TemplateEntity("123", 1)

        /* When */
        val result = templateEntity.toModel()

        /* Then */
        assertEquals(TemplateModel("123", 1), result)
    }

    @Test
    fun `map TemplateModel to TemplateEntity`() {
        /* Given */
        val templateModel = TemplateModel("123", 1)

        /* When */
        val result = TemplateEntity.from(templateModel)

        /* Then */
        assertEquals(TemplateEntity("123", 1), result)
    }
}