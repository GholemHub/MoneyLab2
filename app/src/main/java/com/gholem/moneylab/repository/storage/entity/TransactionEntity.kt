package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    val amount: Int,
    val date: Long,
    val categoryId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {

    fun map(categoryEntity: CategoryEntity): TransactionModel {
        return when(categoryEntity.type) {
            1 -> {
                TransactionModel(
                    category = ExpenseCategoryModel(
                        categoryName = categoryEntity.name,
                        image = categoryEntity.image,
                        id = categoryEntity.id
                    ),
                    amount = amount,
                    date = date,
                    transactionId = id
                )
            }
            else -> {
                TransactionModel(
                    category = CategoryItem.IncomeCategoryModel(
                        categoryName = categoryEntity.name,
                        image = categoryEntity.image,
                        id = categoryEntity.id
                    ),
                    amount = amount,
                    date = date,
                    transactionId = id
                )
            }
        }


    }


    companion object {
        fun from(transaction: TransactionModel): TransactionEntity {
            return when (transaction.category) {
                is ExpenseCategoryModel -> {
                    TransactionEntity(
                        transaction.amount,
                        transaction.date,
                        transaction.category.id ?: 1
                    )
                }
                is CategoryItem.IncomeCategoryModel -> {
                    TransactionEntity(
                        transaction.amount,
                        transaction.date,
                        transaction.category.id ?: 1
                    )
                }
            }
        }

        fun setTransactionEntityId(transaction: TransactionModel): TransactionEntity {
            return when (transaction.category) {
                is ExpenseCategoryModel -> {
                    TransactionEntity(
                        transaction.amount,
                        transaction.date,
                        transaction.category.id ?: 1,
                        transaction.transactionId
                    )
                }
                is CategoryItem.IncomeCategoryModel -> {
                    TransactionEntity(
                        transaction.amount,
                        transaction.date,
                        transaction.category.id ?: 1,
                        transaction.transactionId
                    )
                }
            }
        }
    }
}