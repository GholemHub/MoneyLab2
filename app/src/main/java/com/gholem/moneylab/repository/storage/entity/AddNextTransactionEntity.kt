package com.gholem.moneylab.repository.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gholem.moneylab.domain.model.AddNextTransaction

@Entity
data class AddNextTransactionEntity(
    val category: AddNextTransaction.Category,
    val transaction: AddNextTransaction.Transaction,
    val nextTransaction: AddNextTransaction.NewTransaction
) {
    fun toModel(): AddNextTransaction = AddNextTransaction()

    companion object{
        /*fun from(addNextTransaction: AddNextTransaction): AddNextTransactionEntity =
            AddNextTransactionEntity(

            )*/
    }
}