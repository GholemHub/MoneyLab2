package com.gholem.moneylab.features.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
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
    private val insertCategoryListUseCase: InsertCategoryModelUseCase
) : ViewModel() {

    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()

    var navigation: NavigationLiveData<SplashNavigationEvent> = NavigationLiveData()

    private var listOfCategories = mutableListOf<TransactionCategory>()

    fun getCategory() = viewModelScope.launch {
        listOfCategories = getCategoryListUseCase.run(Unit) as MutableList<TransactionCategory>
        initializeCategoriesIfEmpty()
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

    private fun initializeCategoriesIfEmpty() {
        if (listOfCategories.size == 0) {
            listOfCategories.add(TransactionCategory(R.string.category_others, R.drawable.ic_category_other))
            listOfCategories.add(TransactionCategory(R.string.category_transport,R.drawable.ic_category_transport))
            listOfCategories.add(TransactionCategory( R.string.category_food, R.drawable.ic_category_food))
            listOfCategories.add(TransactionCategory( R.string.category_sport, R.drawable.ic_category_sport))
            saveCategories(listOfCategories)
        }
    }
    private fun saveCategories(caregories: List<TransactionCategory>) = viewModelScope.launch {
        caregories.forEach {
            insertCategoryListUseCase.run(it)
        }
    }

    sealed class UiState() {
        object Loading : UiState()
        object Loaded : UiState()
        object NavigateToDashboard : UiState()
    }
}