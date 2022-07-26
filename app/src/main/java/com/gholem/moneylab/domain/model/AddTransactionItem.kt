package com.gholem.moneylab.domain.model

sealed class AddTransactionItem {
    data class Category(
        val category: TransactionCategory
    ): AddTransactionItem()

    data class Transaction(
        var amount: String = "",
        val date: Long = System.currentTimeMillis()
    ): AddTransactionItem()

    object NewTransaction: AddTransactionItem()
}