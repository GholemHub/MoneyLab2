package com.gholem.moneylab.features.chart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.ChartTransactionItem.Companion.listOfChartTransaction
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase
) : ViewModel() {

    var v: List<Transaction> = emptyList()

    fun createRoomDate() {
        reedDataFromRoom()

        val list: MutableList<ChartTransactionItem.ChartTransaction> = arrayListOf()

        for (transaction in v) {
            val transactionCategory = TransactionCategory.fromId(transaction.category.id)

            val item = ChartTransactionItem.ChartTransaction(
                transactionCategory,
                transaction.amount.toString()
            )
            list.add(item)
        }
        listOfChartTransaction = list
    }

    fun reedDataFromRoom() = viewModelScope.launch {
        v = getTransactionListUseCase.run(Unit)
    }
}