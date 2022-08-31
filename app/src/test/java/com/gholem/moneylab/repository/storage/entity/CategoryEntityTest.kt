package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.TransactionCategory
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryEntityTest {
    @Test
    fun `map CategoryEntity to TransactionCategory`() {
        /* When */
        val result = CategoryEntity("123", 1).toModel()

        /* Then */
        assertEquals(TransactionCategory("123", 1, 0), result)
    }

    @Test
    fun `map TransactionCategory to CategoryEntity`() {
        /* Given */
        val transactionCategory = TransactionCategory("123", 1)

        /* When */
        val result = CategoryEntity.from(transactionCategory)

        /* Then */
        assertEquals(CategoryEntity("123", 1), result)
    }
}