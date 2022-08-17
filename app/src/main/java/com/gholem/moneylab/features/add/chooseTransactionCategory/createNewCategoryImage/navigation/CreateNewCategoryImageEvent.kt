package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategoryImage.navigation

sealed class CreateNewCategoryImageEvent {
    object ToPreviousScreen : CreateNewCategoryImageEvent()
}