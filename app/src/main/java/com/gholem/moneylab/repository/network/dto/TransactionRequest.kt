package com.gholem.moneylab.repository.network.dto

import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel

data class TransactionRequest(
    val category: TransactionCategoryModel,
    val amount: Int,
    val date: Long
) {
    companion object {
        fun fromModel(transactionModel: TransactionModel) = TransactionRequest(
            transactionModel.category,
            transactionModel.amount,
            transactionModel.date
        )
    }
}
