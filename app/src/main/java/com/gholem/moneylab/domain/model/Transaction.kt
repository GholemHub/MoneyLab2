package com.gholem.moneylab.domain.model

data class Transaction(
    var category: TransactionCategory,
    var amount: Int,
    var date: Long
)