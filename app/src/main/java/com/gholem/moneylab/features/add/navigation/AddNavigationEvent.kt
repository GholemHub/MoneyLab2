package com.gholem.moneylab.features.add.navigation

sealed class AddNavigationEvent {
    object ToPreviousScreen : AddNavigationEvent()
    object ToCategoryBottomSheetDialog : AddNavigationEvent()
}