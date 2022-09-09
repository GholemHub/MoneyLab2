package com.gholem.moneylab.features.chart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.chart.adapter.item.ChartTransactionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun fetchTransactionList() = viewModelScope.launch {
        val transactions = getTransactionListUseCase.run(Unit)
        val result: MutableList<ChartTransactionItem> = arrayListOf()

        val mapOfTransactions = getMapOfTransactionsByDate(transactions)

        mapOfTransactions.forEach { entry ->
            result.add(ChartTransactionItem.ChartDate(entry.key))

            entry.value.forEach { transaction ->
                result.add(
                    ChartTransactionItem.ChartTransaction(
                        transaction.category,
                        transaction.amount.toString()
                    )
                )
            }
        }

        Action.ShowDataChartTransactionItem(result).send()
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
        data class ShowDataChartTransactionItem(val list: List<ChartTransactionItem>) : Action()
    }
}