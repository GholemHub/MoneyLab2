package com.gholem.moneylab.features.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.chart.adapter.viewholder.ChartViewHolder
import com.gholem.moneylab.features.history.adapter.item.HistoryTransactionItem
import com.gholem.moneylab.features.history.navigation.HistoryNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber.i
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    var navigation: NavigationLiveData<HistoryNavigationEvent> =
        NavigationLiveData()
    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun navigateToEditTransaction(position: Long) = viewModelScope.launch {
        navigation.emit(HistoryNavigationEvent.ToEditTransaction(position))
    }

    fun fetchTransactionList() = viewModelScope.launch {
        val transactions = getTransactionListUseCase.run(Unit)
        val result: MutableList<HistoryTransactionItem> = arrayListOf()

        val mapOfTransactions = getMapOfTransactionsByDate(transactions)

        mapOfTransactions.forEach { entry ->
            result.add(HistoryTransactionItem.HistoryDate(entry.key))

            entry.value.forEach { transaction ->
                val transactionCategoryItem = when (transaction.category) {
                    is CategoryItem.ExpenseCategoryModel -> {
                        HistoryTransactionItem.HistoryTransaction(
                            transaction.category,
                            transaction.amount.toString(),
                            transaction.transactionId
                        )
                    }
                    is CategoryItem.IncomeCategoryModel -> {
                        HistoryTransactionItem.HistoryTransaction(
                            transaction.category,
                            transaction.amount.toString(),
                            transaction.transactionId
                        )
                    }
                }
                result.add(transactionCategoryItem)
            }
        }
        i("result @@ ${result}")
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

    fun getAllExpends(list: List<HistoryTransactionItem>): String {
        i("List @@ ${list} ")

        var excomes = 0
        var incomes = 0
        list.forEach {
            if (it is HistoryTransactionItem.HistoryTransaction) {
                when (it.category) {
                    is CategoryItem.ExpenseCategoryModel -> {
                        excomes += it.amount.toInt()
                    }
                    is CategoryItem.IncomeCategoryModel -> {
                        incomes += it.amount.toInt()
                    }
                }
            }
        }
        i("excomes.minus(incomes @@ ${excomes} ${incomes}")
        return incomes.minus(excomes).toString()
    }

    sealed class Action {
        data class ShowDataHistoryTransactionItem(val list: List<HistoryTransactionItem>) : Action()
    }
}