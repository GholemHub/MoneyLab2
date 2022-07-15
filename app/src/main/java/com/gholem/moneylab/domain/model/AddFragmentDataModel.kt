package com.gholem.moneylab.domain.model

open class AddFragmentDataModel {
    data class Category(
        val image: Int = 0,
        val name: String = "",
        val id: Int = 0
    ): AddFragmentDataModel()

    data class Transaction(
        val amount: Int = 0,
        val data: String = ""
    ): AddFragmentDataModel()

    data class NewTransaction(
        val add: String
    ): AddFragmentDataModel()
}