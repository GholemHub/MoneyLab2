package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionEntityTest {

    @Test
    fun `map Transaction to TransactionEntity`() {
        /* Given */
        val category = TransactionCategory("1", 1, 1)
        val transaction = Transaction(category, 2, 3)

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
            Transaction(
                category = TransactionCategory("name", 1, 1),
                amount = 1,
                date = 1
            ), result
        )
    }
}