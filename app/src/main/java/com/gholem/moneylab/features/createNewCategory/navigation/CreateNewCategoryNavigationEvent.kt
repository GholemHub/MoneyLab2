package com.gholem.moneylab.features.createNewCategory.navigation

sealed class CreateNewCategoryNavigationEvent {
    object ToPreviousScreen : CreateNewCategoryNavigationEvent()
    object ToImagePicker : CreateNewCategoryNavigationEvent()
}