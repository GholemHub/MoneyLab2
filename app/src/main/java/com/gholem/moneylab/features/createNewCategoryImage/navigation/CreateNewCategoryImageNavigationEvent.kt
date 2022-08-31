package com.gholem.moneylab.features.createNewCategoryImage.navigation

sealed class CreateNewCategoryImageNavigationEvent {
    object ToPreviousScreen : CreateNewCategoryImageNavigationEvent()
}