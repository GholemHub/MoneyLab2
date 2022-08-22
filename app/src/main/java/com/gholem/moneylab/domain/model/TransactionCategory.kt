package com.gholem.moneylab.domain.model

import androidx.annotation.DrawableRes

data class TransactionCategory(
    val id: Int,
    val categoryName: String,
    @DrawableRes val image: Int
)