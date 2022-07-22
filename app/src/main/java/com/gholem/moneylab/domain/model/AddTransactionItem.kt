package com.gholem.moneylab.domain.model

open class AddTransactionItem {
    data class Category(
        val image: Int,
        val name: String,
        val id: Int
    ): AddTransactionItem()

    data class Transaction(
        val amount: Int,
        val data: String
    ): AddTransactionItem()

    object NewTransaction: AddTransactionItem()
}