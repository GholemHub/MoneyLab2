package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory

@Entity(tableName = "transaction_table")
data class TransactionEntity(

    val amount: Int,
    val date: Long,
    val categoryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun map(categoryEntity: CategoryEntity): Transaction =
        Transaction(
            category = TransactionCategory(
                categoryName = categoryEntity.name,
                image = categoryEntity.image,
                id = categoryEntity.id
            ),
            amount = amount,
            date = date
        )

    companion object {
        fun from(transaction: Transaction): TransactionEntity =
            TransactionEntity(
                transaction.amount,
                transaction.date,
                transaction.category.id ?: 1
            )

        fun setTransactionEntityId(transaction: Transaction, id: Long): TransactionEntity =
            TransactionEntity(
                transaction.amount,
                transaction.date,
                transaction.category.id ?: 1,
                id
            )
    }
}