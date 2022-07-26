package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    @PrimaryKey val id: Long,
    val categoryId: Int,
    val amount: Int,
    val date: String
) {

    fun toModel(): Transaction = Transaction(
        category = TransactionCategory.fromId(categoryId),
        amount = amount,
        date = date
    )

    companion object {
        fun from(transaction: Transaction): TransactionEntity =
            TransactionEntity(
                System.currentTimeMillis(),
                transaction.category.id,
                transaction.amount,
                transaction.date
            )
    }
}