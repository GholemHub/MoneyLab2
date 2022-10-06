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
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val insertTransactionModelUseCase: InsertTransactionModelUseCase,
    private val fetchTransactionModelUseCase: FetchTransactionModelUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    private val LAST_MONTH = 2592000000L

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val adapterData: MutableList<ChartItem> = mutableListOf()

    val navigation: NavigationLiveData<ChartNavigationEvent> =
        NavigationLiveData()

    private fun createNotDuplicatedTransactionModel(list: List<TransactionModel>): List<TransactionModel> {

        val chartCategoryModelList = mutableListOf<TransactionModel>()
        val chartCategories = list
            .groupBy { it.category }
            .mapValues { it.value.sumOf { it.amount } }

        chartCategories.forEach {
            chartCategoryModelList.add(
                TransactionModel(
                    it.key,
                    it.value,
                    System.currentTimeMillis(),
                    System.currentTimeMillis()
                )
            )
        }

        return sortTransactionList(chartCategoryModelList)
    }

    fun getTransactionList() = viewModelScope.launch {
        val transactions = fetchTransactionModelUseCase.run(Unit)
        Action.FetchTransactions(transactions).send()

        val listOfTransaction = getTransactionListUseCase.run(Unit)
        val last30Days = getLast30DaysFromList(listOfTransaction)
        val listOfSortedTransactions = createNotDuplicatedTransactionModel(last30Days)
        Action.ShowTransactions(listOfSortedTransactions).send()
    }

    private fun getLast30DaysFromList(list: List<TransactionModel>): List<TransactionModel> {
        val lastMonth = System.currentTimeMillis() - LAST_MONTH

        return list.filter { it.date >= lastMonth }
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