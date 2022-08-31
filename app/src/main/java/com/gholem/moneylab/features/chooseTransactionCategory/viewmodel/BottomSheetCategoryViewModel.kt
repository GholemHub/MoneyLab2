package com.gholem.moneylab.features.chooseTransactionCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetCategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    var listOfCategories = mutableListOf<TransactionCategory>()
    var navigation: NavigationLiveData<BottomSheetCategoryEvent> = NavigationLiveData()

    fun getCategories() = viewModelScope.launch {
        listOfCategories = getCategoryListUseCase.run(Unit) as MutableList<TransactionCategory>
        Action.ShowData(listOfCategories).send()
    }

    fun navigateToAddTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToPreviousScreen)
    }

    fun navigateToCreateNewTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToCreateNewCategory)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowData(val list: List<TransactionCategory>) : Action()
    }
}