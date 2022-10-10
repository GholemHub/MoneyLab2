package com.gholem.moneylab.features.chart.viewmodel

import android.text.format.DateFormat
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
    private val LAST_MONTH = 2678400000L
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

    private fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
        return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
    }

    private fun convertDate(): String? {
        getTransactionList()
        return setMonth()
    }
    private fun setMonth(): String? {
        var lastMonth = System.currentTimeMillis()
        lastMonth = lastMonth + COUNT_MONTH

        return convertDate(lastMonth.toString(), "MM/yyyy")
    }

    private fun sortTransactionList(listOfTransaction: List<TransactionModel>) =
        listOfTransaction.sortedByDescending { it.amount }

    private fun getTransactionList() = viewModelScope.launch {

        val listOfTransaction = getTransactionListUseCase.run(Unit)
        val last30Days = getMonthFromList(listOfTransaction)
        val listOfSortedTransactions = createNotDuplicatedTransactionModel(last30Days)
        Action.ShowTransactions(listOfSortedTransactions).send()
    }

    private fun getMonthFromList(list: List<TransactionModel>): List<TransactionModel> {

        val startOfMonth = Calendar.getInstance()
        val endOfMonth = Calendar.getInstance()

        startOfMonth.timeInMillis = startOfMonth.timeInMillis + (COUNT_MONTH)
        startOfMonth[Calendar.DAY_OF_MONTH] = 1

        endOfMonth.timeInMillis = endOfMonth.timeInMillis + (COUNT_MONTH)
        endOfMonth[Calendar.DAY_OF_MONTH] = endOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)

        return list.filter { it.date >= startOfMonth.timeInMillis && it.date <= endOfMonth.timeInMillis }
    }

    fun saveNewTransaction(item: TransactionModel) = viewModelScope.launch {
        insertTransactionModelUseCase.run(item)
    }

    fun deductCountMonth(): String? {
        COUNT_MONTH -= LAST_MONTH
        return  convertDate()
    }
    fun sumCountMonth(): String? {
        COUNT_MONTH += LAST_MONTH
        return convertDate()
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class FetchTransactions(val transactions: List<TransactionModel>) : Action()
        data class ShowTransactions(val list: List<TransactionModel>) : Action()
    }
}