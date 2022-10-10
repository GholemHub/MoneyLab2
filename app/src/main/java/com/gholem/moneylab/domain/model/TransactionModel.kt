package com.gholem.moneylab.domain.model

data class TransactionModel(
    val category: TransactionCategoryModel,
    val amount: Int,
    val date: Long,
    val transactionId: Long
)