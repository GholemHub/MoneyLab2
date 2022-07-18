package com.gholem.moneylab.domain.model

open class AddNextTransaction {
    data class Category(
        val image: Int,
        val name: String,
        val id: Int
    ): AddNextTransaction()

    data class Transaction(
        val amount: Int,
        val data: String
    ): AddNextTransaction()

    data class NewTransaction(
        val add: String
    ): AddNextTransaction()
}