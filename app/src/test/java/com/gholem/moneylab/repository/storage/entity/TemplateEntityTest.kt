package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.TemplateModel
import org.junit.Assert
import org.junit.Test

class TemplateEntityTest {
    @Test
    fun `Entity ToModel trigger`() {
        /* Given */
        val templateEntity = TemplateEntity("123", 1)
        /* When */
        val result = templateEntity.toModel()
        /* Then */
        Assert.assertEquals(TemplateModel("123", 1), result)
    }

    @Test
    fun `Entity from trigger`() {
        /* Given */
        val templateModel = TemplateModel("123", 1)

        /* When */
        val result = TemplateEntity.from(templateModel)

        /* Then */
        Assert.assertEquals(TemplateEntity("123", 1), result)
    }
}