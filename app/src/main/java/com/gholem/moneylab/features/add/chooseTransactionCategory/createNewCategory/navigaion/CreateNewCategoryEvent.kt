package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.navigaion

sealed class CreateNewCategoryEvent {
    object ToPreviousScreen : CreateNewCategoryEvent()
    object ToImagePicker : CreateNewCategoryEvent()
}