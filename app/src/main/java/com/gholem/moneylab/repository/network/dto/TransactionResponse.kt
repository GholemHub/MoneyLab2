package com.gholem.moneylab.repository.network.dto

import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel

data class TransactionResponse(
    val category: TransactionCategoryModel,
    val amount: Int,
    val date: Long
) {

    fun toModel(): TransactionModel = TransactionModel(category, amount, date)
}