package com.gholem.moneylab.features.add.navigation

sealed class BottomSheetCategoryEvent {
    object ToPreviousScreen : BottomSheetCategoryEvent()

    data class BottomSheetCategoryData(val type: Int) : BottomSheetCategoryEvent()
}