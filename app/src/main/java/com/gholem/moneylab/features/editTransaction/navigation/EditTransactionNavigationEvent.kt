package com.gholem.moneylab.features.editTransaction.navigation

import com.gholem.moneylab.features.add.navigation.AddNavigationEvent

sealed class EditTransactionNavigationEvent {
    object ToPreviousScreen : EditTransactionNavigationEvent()
    object ToCategoryBottomSheetDialog : EditTransactionNavigationEvent()
}