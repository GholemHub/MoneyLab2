package com.gholem.moneylab.features.add.navigation

sealed class BottomSheetCategoryEvent {
    object ToPreviousScreen : BottomSheetCategoryEvent()
}