package com.gholem.moneylab.domain.model

import androidx.annotation.DrawableRes

sealed class CategoryItem {

    data class IncomeCategoryModel(
        val categoryName: String,
        @DrawableRes val image: Int,
        val id: Long? = null
    ) : CategoryItem()

    data class ExpenseCategoryModel(
        val categoryName: String,
        @DrawableRes val image: Int,
        val id: Long? = null
    ) : CategoryItem()
}