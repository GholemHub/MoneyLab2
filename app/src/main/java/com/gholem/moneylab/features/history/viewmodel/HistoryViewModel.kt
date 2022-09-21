package com.gholem.moneylab.features.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import com.gholem.moneylab.features.history.navigation.HistoryNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    var navigation: NavigationLiveData<HistoryNavigationEvent> =
        NavigationLiveData()

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun navigateToEditTransaction(_position: Long) = viewModelScope.launch {
        navigation.emit(HistoryNavigationEvent.ToEditTransaction(_position))
    }

    fun fetchTransactionList() = viewModelScope.launch {
        val transactions = getTransactionListUseCase.run(Unit)
        val result: MutableList<HistoryTransactionItem> = arrayListOf()

        val mapOfTransactions = getMapOfTransactionsByDate(transactions)

        mapOfTransactions.forEach { entry ->
            result.add(HistoryTransactionItem.HistoryDate(entry.key))

            entry.value.forEach { transaction ->
                result.add(
                    HistoryTransactionItem.HistoryTransaction(
                        transaction.category,
                        transaction.amount.toString(),
                        transaction.transactionId
                    )
                )
            }
        }

        Action.ShowDataHistoryTransactionItem(result).send()
    }

    private fun getMapOfTransactionsByDate(list: List<TransactionModel>): Map<Long, List<TransactionModel>> {
        val result = mutableMapOf<Long, List<TransactionModel>>()
        val dateSet = list.map { it.date }.sortedDescending().toSet()

        dateSet.forEach { uniqueDate ->
            result[uniqueDate] = list.filter { it.date == uniqueDate }
        }

        return result
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowDataHistoryTransactionItem(val list: List<HistoryTransactionItem>) : Action()
    }
}