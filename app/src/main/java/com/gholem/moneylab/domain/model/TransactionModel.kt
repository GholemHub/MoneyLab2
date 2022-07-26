package com.gholem.moneylab.domain.model

data class TransactionModel(
    val id: Int,
    val nameCategory: String,
    val image: Int,
    val amount: Int,
    val date: String
) {

}