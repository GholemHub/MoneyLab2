package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.ExcomeCategoryModel
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionEntityTest {

    @Test
    fun `map Transaction to TransactionEntity`() {
        /* Given */
        val category = ExcomeCategoryModel("1", 1, 1)
        val transaction = TransactionModel(category, 2, 3, 1)

        /* When */
        val result = TransactionEntity.from(transaction)

        /* Then */
        assertEquals(TransactionEntity(2, 3, 1), result)
    }

    @Test
    fun `map TransactionEntity to Transaction`() {
        /* Given */
        val transactionEntity = TransactionEntity(1, 1, 1, 1)
        val categoryEntity = CategoryEntity("name", 1, 1)

        /* When */
        val result = transactionEntity.map(categoryEntity)

        /* Then */
        assertEquals(
            TransactionModel(
                category = ExcomeCategoryModel("name", 1, 1),
                amount = 1,
                date = 1,
                1
            ), result
        )
    }
}