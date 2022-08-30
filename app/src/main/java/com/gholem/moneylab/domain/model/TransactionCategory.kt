package com.gholem.moneylab.domain.model

import androidx.annotation.DrawableRes

data class TransactionCategory(
    val categoryName: String,
    @DrawableRes val image: Int,
    val id: Long? = null
)