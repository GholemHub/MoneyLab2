package com.gholem.moneylab.features.createNewCategoryImage.navigation

sealed class CreateNewCategoryImageEvent {
    object ToPreviousScreen : CreateNewCategoryImageEvent()
}