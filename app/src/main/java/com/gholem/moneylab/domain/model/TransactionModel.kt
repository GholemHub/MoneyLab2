package com.gholem.moneylab.domain.model

data class TransactionModel(
    var category: TransactionCategoryModel,
    var amount: Int,
    var date: Long,
    val transactionId: Long
)