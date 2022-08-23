package com.gholem.moneylab.features.createNewCategoryImage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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