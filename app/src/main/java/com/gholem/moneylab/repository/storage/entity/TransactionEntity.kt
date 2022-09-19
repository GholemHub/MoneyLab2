package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.domain.model.TransactionCategoryModel

@Entity(tableName = "transaction_table")
data class TransactionEntity(

    val amount: Int,
    val date: Long,
    val categoryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun map(categoryEntity: CategoryEntity): TransactionModel =
        TransactionModel(
            category = TransactionCategoryModel(
                categoryName = categoryEntity.name,
                image = categoryEntity.image,
                id = categoryEntity.id
            ),
            amount = amount,
            date = date
        )

    companion object {
        fun from(transaction: TransactionModel): TransactionEntity =
            TransactionEntity(
                transaction.amount,
                transaction.date,
                transaction.category.id ?: 1
            )

        fun setTransactionEntityId(transaction: TransactionModel, id: Long): TransactionEntity =
            TransactionEntity(
                transaction.amount,
                transaction.date,
                transaction.category.id ?: 1,
                id
            )
    }
}