package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.TransactionModel

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    @PrimaryKey val id: Int,
    val nameCategory: String,
    val image: Int,
    val amount: Int,
    val date: String
) {

    fun toModel(): TransactionModel = TransactionModel(
        id = id,
        nameCategory = nameCategory,
        image = image,
        amount = amount,
        date = date
    )

    companion object {
        fun from(transaction: TransactionModel): TransactionEntity =
            TransactionEntity(
                transaction.id,
                transaction.nameCategory,
                transaction.image,
                transaction.amount,
                transaction.date
            )
    }
}