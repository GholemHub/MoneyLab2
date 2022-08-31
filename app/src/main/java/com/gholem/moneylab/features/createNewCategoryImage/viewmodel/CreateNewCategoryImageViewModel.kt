package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCategoryImageViewModel @Inject constructor() : ViewModel() {

    var navigation: NavigationLiveData<CreateNewCategoryImageNavigationEvent> =
        NavigationLiveData()

    fun navigateToPreviousScreen() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryImageNavigationEvent.ToPreviousScreen)
    }
}