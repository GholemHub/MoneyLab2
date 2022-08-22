package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategoryImage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.viewmodel.CreateNewCategoryViewModel
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategoryImage.navigation.CreateNewCategoryImageEvent
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCategoryImageViewModel @Inject constructor() : ViewModel() {

    val navigation: NavigationLiveData<CreateNewCategoryImageEvent> =
        NavigationLiveData()


    fun navigateToPreviousScreen() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryImageEvent.ToPreviousScreen)
    }

}