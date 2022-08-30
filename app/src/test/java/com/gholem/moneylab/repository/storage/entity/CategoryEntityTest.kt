package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.TransactionCategory
import org.junit.Assert
import org.junit.Test

class CategoryEntityTest {
    @Test
    fun `ToModel trigger`() {
        /* Given */

        /* When */
        val result = CategoryEntity("123", 1).toModel()
        /* Then */
        Assert.assertEquals(TransactionCategory("123", 1, 0), result)
    }

    @Test
    fun `Entity from trigger`() {
        /* Given */
        val transactionCategory = TransactionCategory("123", 1)
        /* When */
        val result = CategoryEntity.from(transactionCategory)
        /* Then */
        Assert.assertEquals(CategoryEntity("123", 1), result)
    }
}