package com.gholem.moneylab.domain.model

sealed class AddTransactionItem {
    data class Category(
        var category: TransactionCategory
    ) : AddTransactionItem()

    data class Transaction(
        var amount: String = "",
        var date: Long = System.currentTimeMillis()
    ) : AddTransactionItem()

    object NewTransaction : AddTransactionItem()

    companion object {
        fun getDefaultItems(): List<AddTransactionItem> =
            listOf(
                Category(TransactionCategory.getDefault()),
                Transaction(),
                NewTransaction
            )
    }
}