package com.gholem.moneylab.domain.model

import com.gholem.moneylab.R

sealed class NewCategoryImageItem {
    data class Image(
        var id: Int,
        var image: Int
    ) : NewCategoryImageItem()

    companion object {
        fun getImages(): List<NewCategoryImageItem> = listOf(
            Image(0,R.drawable.ic_category_food),
            Image(1,R.drawable.ic_app_logo),
            Image(2,R.drawable.ic_category_transport),
            Image(3,R.drawable.ic_category_food),
            Image(4,R.drawable.ic_app_logo),
            Image(5,R.drawable.ic_category_transport),
            Image(6,R.drawable.ic_category_food),
            Image(7,R.drawable.ic_app_logo),
            Image(8,R.drawable.ic_category_transport)
        )
    }
}