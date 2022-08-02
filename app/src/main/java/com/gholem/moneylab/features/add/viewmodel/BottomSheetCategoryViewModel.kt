package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.add.navigation.BottomSheetCategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetCategoryViewModel @Inject constructor() : ViewModel() {

    val navigation: NavigationLiveData<BottomSheetCategoryEvent> = NavigationLiveData()

    fun navigateToAddTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToPreviousScreen)
    }
    fun init() {
    }
}