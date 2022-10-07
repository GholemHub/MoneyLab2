package com.gholem.moneylab.features.chart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionModelUseCase
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.features.chart.domain.FetchTransactionModelUseCase
import com.gholem.moneylab.features.chart.navigation.ChartNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val insertTransactionModelUseCase: InsertTransactionModelUseCase,
    private val fetchTransactionModelUseCase: FetchTransactionModelUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    var COUNT_MONTH = 0L
    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val adapterData: MutableList<ChartItem> = mutableListOf()

    val navigation: NavigationLiveData<ChartNavigationEvent> =
        NavigationLiveData()

    private fun createNotDuplicatedTransactionModel(list: List<TransactionModel>): List<TransactionModel> {

        return sortTransactionList(list.groupBy { it.category }
            .mapValues { it.value.sumOf { it.amount } }.map {
                TransactionModel(
                    it.key,
                    it.value,
                    0L,
                    0L
                )
            })
    }

    fun getTransactionList() = viewModelScope.launch {
        val listOfTransaction = getTransactionListUseCase.run(Unit)
        val last30Days = getMonthFromList(listOfTransaction)
        val listOfSortedTransactions = createNotDuplicatedTransactionModel(last30Days)
        Action.ShowTransactions(listOfSortedTransactions).send()
    }

    private fun getMonthFromList(list: List<TransactionModel>): List<TransactionModel> {

        var startOfMonth = Calendar.getInstance()
        var endOfMonth = Calendar.getInstance()
        startOfMonth.timeInMillis = System.currentTimeMillis() + (COUNT_MONTH)
        startOfMonth[Calendar.DAY_OF_MONTH] = 1

        endOfMonth.timeInMillis = System.currentTimeMillis() + (COUNT_MONTH)
        endOfMonth[Calendar.DAY_OF_MONTH] = 31

        var lista = mutableListOf<TransactionModel>()
        if (list.filter { it.date >= startOfMonth.timeInMillis && it.date <= endOfMonth.timeInMillis }.size == 0) {
            lista = mutableListOf()
        } else {
            lista.addAll(list.filter { it.date >= startOfMonth.timeInMillis && it.date <= endOfMonth.timeInMillis })
        }
        return lista
    }

    fun saveNewTransaction(item: TransactionModel) = viewModelScope.launch {
        insertTransactionModelUseCase.run(item)
    }

    private fun sortTransactionList(listOfTransaction: List<TransactionModel>) =
        listOfTransaction.sortedByDescending { it.amount }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }


    sealed class Action {
        data class FetchTransactions(val transactions: List<TransactionModel>) : Action()
        data class ShowTransactions(val list: List<TransactionModel>) : Action()
    }
}