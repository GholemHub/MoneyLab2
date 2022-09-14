package com.gholem.moneylab.features.editTransaction.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.chart.viewmodel.ChartViewModel
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class EditTransactionViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val insertTransactionsModelUseCase: InsertTransactionsModelUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    fun getTransactionInfo(positionItem: Long) =  viewModelScope.launch{
        val transactions = getTransactionListUseCase.run(Unit)
        transactions[positionItem.toInt()].amount

        val currentTransaction = Transaction(
            transactions[positionItem.toInt()].category,
            transactions[positionItem.toInt()].amount,
            transactions[positionItem.toInt()].date
        )
        Action.GetCurrentTransaction(currentTransaction)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class GetCurrentTransaction(val transaction: Transaction) : Action()
    }
}