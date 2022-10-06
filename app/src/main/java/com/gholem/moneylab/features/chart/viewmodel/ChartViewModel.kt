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
import timber.log.Timber.i
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val insertTransactionModelUseCase: InsertTransactionModelUseCase,
    private val fetchTransactionModelUseCase: FetchTransactionModelUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    val LAST_MONTH = 2678400000L
    var COUNT_MONTH = 0L
    private val TIME_IN_MILI = 1000000000L

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val adapterData: MutableList<ChartItem> = mutableListOf()

    val navigation: NavigationLiveData<ChartNavigationEvent> =
        NavigationLiveData()

    private fun createNotDuplicatedTransactionModel(list: List<TransactionModel>): List<TransactionModel> {

        return sortTransactionList(list.groupBy { it.category }
            .mapValues { it.value.sumOf { it.amount } }.map {
                i("it.value:: ${it.value}")
                TransactionModel(
                    it.key,
                    it.value,
                    TIME_IN_MILI,
                    TIME_IN_MILI
                )
            })
    }

    fun getTransactionList() = viewModelScope.launch {
        val listOfTransaction = getTransactionListUseCase.run(Unit)
        val last30Days = getMonthFromList(listOfTransaction)
        val listOfSortedTransactions = createNotDuplicatedTransactionModel(last30Days)
        i("listOfSortedTransactions :: ${listOfSortedTransactions.size}")
        Action.ShowTransactions(listOfSortedTransactions).send()
    }

    private fun getLast30DaysFromList(list: List<TransactionModel>): List<TransactionModel> {
        val lastMonth = System.currentTimeMillis() - LAST_MONTH

        return list.filter { it.date >= lastMonth }
    }

    private fun getMonthFromList(list: List<TransactionModel>): List<TransactionModel> {
        val lastMonth = System.currentTimeMillis() - (COUNT_MONTH)

        var c1 = Calendar.getInstance()
        var c2 = Calendar.getInstance()
        c1.timeInMillis = System.currentTimeMillis() + (COUNT_MONTH)// this takes current date
        c1[Calendar.DAY_OF_MONTH] = 1

        c2.timeInMillis = System.currentTimeMillis() + (COUNT_MONTH)// this takes current date
        c2[Calendar.DAY_OF_MONTH] = 31
        //i("Day :: ${c1.timeInMillis} ${c2.timeInMillis}")
        i("size :: ${list.filter { it.date >= c1.timeInMillis && it.date <= c2.timeInMillis }.size}")
        var lista = mutableListOf<TransactionModel>()
        if (list.filter { it.date >= c1.timeInMillis && it.date <= c2.timeInMillis }.size == 0){
            lista = mutableListOf()
        }
        else{
            lista.addAll(list.filter { it.date >= c1.timeInMillis && it.date <= c2.timeInMillis })
        }

        i("size2 :: ${lista.size}")
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