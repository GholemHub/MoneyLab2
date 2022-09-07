package com.gholem.moneylab.features.planning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.planning.adapter.item.Data
import com.gholem.moneylab.features.planning.domain.FetchTransactionModelUseCase
import com.gholem.moneylab.features.planning.navigation.PlanningNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanningViewModel @Inject constructor(
    private val insertTransactionsModelUseCase: InsertTransactionsModelUseCase,
    private val fetchTransactionModelUseCase: FetchTransactionModelUseCase
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val navigation: NavigationLiveData<PlanningNavigationEvent> =
        NavigationLiveData()

    fun fetchTransactions() = viewModelScope.launch {
        val transactions = fetchTransactionModelUseCase.run(Unit)
        Action.ShowTransactions(transactions).send()
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(PlanningNavigationEvent.ToChartScreen)
    }

    fun saveNewTransactionFromPerson(data: Data) = viewModelScope.launch {
        insertTransactionsModelUseCase.run(listOf())
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowTransactions(val transactions: List<TransactionModel>) : Action()
        data class SelectPerson(val personId: Int) : Action()
    }
}
