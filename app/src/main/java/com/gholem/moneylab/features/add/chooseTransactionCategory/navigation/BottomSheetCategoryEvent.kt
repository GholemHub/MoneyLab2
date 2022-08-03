package com.gholem.moneylab.features.add.chooseTransactionCategory.navigation

sealed class BottomSheetCategoryEvent {
    object ToPreviousScreen : BottomSheetCategoryEvent()
}