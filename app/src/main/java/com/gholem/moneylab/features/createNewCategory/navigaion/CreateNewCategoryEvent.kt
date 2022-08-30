package com.gholem.moneylab.features.createNewCategory.navigaion

sealed class CreateNewCategoryEvent {
    object ToPreviousScreen : CreateNewCategoryEvent()
    object ToImagePicker : CreateNewCategoryEvent()
}