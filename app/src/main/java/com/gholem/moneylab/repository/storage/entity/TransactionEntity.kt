package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    val amount: Int,
    val date: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    companion object {
        fun from(transaction: Transaction): TransactionEntity =
            TransactionEntity(
                transaction.amount,
                transaction.date
            )
    }
}