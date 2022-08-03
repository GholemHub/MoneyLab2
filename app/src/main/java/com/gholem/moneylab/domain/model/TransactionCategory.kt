package com.gholem.moneylab.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gholem.moneylab.R

enum class TransactionCategory(
    val id: Int,
    @StringRes val categoryName: Int,
    @DrawableRes val image: Int
) {
    OTHER(0, R.string.category_other, R.drawable.ic_category_other),
    FOOD(1, R.string.category_food, R.drawable.ic_category_food),
    TRANSPORT(2, R.string.category_transport, R.drawable.ic_category_transport),
    SPORT(3, R.string.category_sport, R.drawable.ic_category_sport);


    companion object {

        fun getDefault() = OTHER

        fun fromId(id: Int): TransactionCategory {
            return when (id) {
                0 -> OTHER
                1 -> FOOD
                2 -> TRANSPORT
                3 -> SPORT
                else -> throw IllegalArgumentException("Wrong category Id")
            }
        }
    }
}