package com.gholem.moneylab.features.chooseTransactionCategory.navigation

sealed class BottomSheetCategoryEvent {
    object ToPreviousScreen : BottomSheetCategoryEvent()
    object ToCreateNewCategory : BottomSheetCategoryEvent()
}