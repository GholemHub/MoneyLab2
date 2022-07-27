package com.gholem.moneylab.domain.model

data class Transaction(
    val category: TransactionCategory,
    val amount: Int,
    val date: String
)