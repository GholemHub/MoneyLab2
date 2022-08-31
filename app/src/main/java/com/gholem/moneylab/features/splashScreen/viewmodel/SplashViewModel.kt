package com.gholem.moneylab.features.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoriesModelUseCase
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val insertCategoriesModelUseCase: InsertCategoriesModelUseCase
) : ViewModel() {

    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()
    var navigation: NavigationLiveData<SplashNavigationEvent> = NavigationLiveData()

    fun getCategoriesAndSetDefault(listOfDefaultCategories: List<TransactionCategory>) =
        viewModelScope.launch {
            initializeCategoriesIfEmpty(getCategoryListUseCase.run(Unit).toMutableList(), listOfDefaultCategories)
        }

    fun init() = viewModelScope.launch {
        _uiState.send(UiState.Loading)
        delay(3000) { _uiState.send(UiState.Loaded) }
        delay(1000) { _uiState.send(UiState.NavigateToDashboard) }
    }

    fun goToAuthentication() {
        navigation.emit(SplashNavigationEvent.ToAuthentication)
    }

    fun goToDashboard() {
        navigation.emit(SplashNavigationEvent.ToDashboard)
        bottomNavigationVisibilityBus.changeVisibility(true)
    }

    private suspend fun delay(timeMillis: Long, action: suspend () -> Unit) {
        delay(timeMillis)
        action.invoke()
    }

    private fun initializeCategoriesIfEmpty(listOfCategories: MutableList<TransactionCategory>,listOfDefaultCategories: List<TransactionCategory>) {
        if (listOfCategories.size == 0) {
            listOfCategories.addAll(listOfDefaultCategories)
            saveCategories(listOfCategories)
        }
    }

    private fun saveCategories(categories: List<TransactionCategory>) = viewModelScope.launch {
        insertCategoriesModelUseCase.run(categories)
    }

    sealed class UiState() {
        object Loading : UiState()
        object Loaded : UiState()
        object NavigateToDashboard : UiState()
    }
}