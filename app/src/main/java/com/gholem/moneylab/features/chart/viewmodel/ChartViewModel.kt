package com.gholem.moneylab.features.chart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
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

    fun createRoomDate() = viewModelScope.launch {
        val transactionsFromDB = getTransactionListUseCase.run(Unit)
        val list: MutableList<ChartTransactionItem> = arrayListOf()

        val mapOfTransactions = getMapOfTransactionsByDate(transactionsFromDB)

        mapOfTransactions.forEach { entry ->
            list.add(ChartTransactionItem.ChartDate(entry.key.toLong()))

            entry.value.forEach { transaction ->
                list.add(
                    ChartTransactionItem.ChartTransaction(
                        transaction.category,
                        transaction.amount.toString()
                    )
                )
            }
        }

        Action.ShowData(list).send()
    }

    private fun getMapOfTransactionsByDate(list: List<Transaction>): Map<Long, List<Transaction>> {
        var result = mutableMapOf<Long, List<Transaction>>()
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
        data class ShowData(val list: List<ChartTransactionItem>) : Action()
    }
}