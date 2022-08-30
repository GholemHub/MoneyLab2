package com.gholem.moneylab.features.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.R
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

    fun getCategories() = viewModelScope.launch {
        checkIfCategoriesIsEmpty()
    }

    fun init() = viewModelScope.launch {
        _uiState.send(UiState.Loading)
        delay(3000) { _uiState.send(UiState.Loaded) }
        delay(1000) { _uiState.send(UiState.NavigateToDashboard) }
    }

    fun goToDashboard() {
        navigation.emit(SplashNavigationEvent.ToDashboard)
        bottomNavigationVisibilityBus.changeVisibility(true)
    }

    private suspend fun delay(timeMillis: Long, action: suspend () -> Unit) {
        delay(timeMillis)
        action.invoke()
    }

    private suspend fun checkIfCategoriesIsEmpty() {
        val listOfCategories = getCategoryListUseCase.run(Unit).toMutableList()

        if (listOfCategories.isEmpty()) {
            listOfCategories.add(TransactionCategory("Others", R.drawable.ic_category_other))
            listOfCategories.add(
                TransactionCategory(
                    "Transport",
                    R.drawable.ic_category_transport
                )
            )
            listOfCategories.add(TransactionCategory("Food", R.drawable.ic_category_food))
            listOfCategories.add(TransactionCategory("Sport", R.drawable.ic_category_sport))
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