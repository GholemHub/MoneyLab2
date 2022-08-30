package com.gholem.moneylab.repository.storage.entity

import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionEntityTest {
    @Test
    fun `Entity from trigger`() {
        /* Given */
        val category = TransactionCategory("1",1,1)
        val transaction = Transaction(category,2,3)

        /* When */
        val result = TransactionEntity.from(transaction)

        /* Then */
        assertEquals(TransactionEntity(2,3), result)
    }
}