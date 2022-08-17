package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.navigaion.CreateNewCategoryEvent
import com.gholem.moneylab.features.add.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCategoryViewModel  @Inject constructor() : ViewModel() {

    val navigation: NavigationLiveData<CreateNewCategoryEvent> = NavigationLiveData()

    fun navigateToImagePicker() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryEvent.ToImagePicker)
    }

}