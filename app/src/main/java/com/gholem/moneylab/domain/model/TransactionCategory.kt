package com.gholem.moneylab.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gholem.moneylab.R


enum class TransactionCategory(
    val id: Int,
    @StringRes val categoryName: Int,
    @DrawableRes val image: Int
){
    FOOD(0, R.string.category_food, R.drawable.ic_app_logo),
    TRANSPORT(1, R.string.category_transport, R.drawable.ic_app_logo),
    SPORT(2, R.string.category_sport, R.drawable.ic_app_logo),
    OTHER(3, R.string.category_sport, R.drawable.ic_app_logo);

    companion object{
        fun fromId(id: Int): TransactionCategory{
            return when(id){
                0 -> FOOD
                1 -> TRANSPORT
                2 -> SPORT
                else -> throw IllegalArgumentException("Wrong category Id")
            }
        }
    }

}