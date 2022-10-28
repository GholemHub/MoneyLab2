package com.gholem.moneylab.domain.model

data class TransactionModel(
    val category: CategoryItem,
    val amount: Int,
    val date: Long,
    val transactionId: Long
)