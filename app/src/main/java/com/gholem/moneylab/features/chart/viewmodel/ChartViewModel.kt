package com.gholem.moneylab.features.chart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.R
import com.gholem.moneylab.domain.model.ChartTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber.i
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    init {
        getCategory()
    }

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    var listOfCategories = mutableListOf<TransactionCategory>()

    fun getCategory() = viewModelScope.launch {
        listOfCategories = getCategoryListUseCase.run(Unit) as MutableList<TransactionCategory>
        checkIfCategoriesIsEmpty()
        Action.ShowData2(listOfCategories).send()
        //i("SHOW data: ${getCategoryListUseCase.run(Unit).get(0).categoryName}")
    }

    private fun checkIfCategoriesIsEmpty() {
        if (listOfCategories.isEmpty()) {
            listOfCategories.add(TransactionCategory(0, "Others", R.drawable.ic_category_other))
            listOfCategories.add(
                TransactionCategory(
                    1,
                    "Transport",
                    R.drawable.ic_category_transport
                )
            )
            listOfCategories.add(TransactionCategory(2, "Food", R.drawable.ic_category_food))
            listOfCategories.add(TransactionCategory(3, "Sport", R.drawable.ic_category_sport))
        }
    }

    fun createRoomDate() = viewModelScope.launch {
        val transactionsFromDB = getTransactionListUseCase.run(Unit)
        val list: MutableList<ChartTransactionItem> = arrayListOf()

        val mapOfTransactions = getMapOfTransactionsByDate(transactionsFromDB)

        mapOfTransactions.forEach { entry ->
            list.add(ChartTransactionItem.ChartDate(entry.key.toLong()))

            entry.value.forEach { transaction ->
                list.add(
                    ChartTransactionItem.ChartTransaction(
                        fromId(transaction.categoryId),
                        transaction.amount.toString()
                    )
                )
            }
        }

        Action.ShowData(list).send()
    }

    fun fromId(id: Int): TransactionCategory {
        listOfCategories.forEach {
            if (it.id == id) {
                return it
            }
        }

        return TransactionCategory(0,"Empty", R.drawable.ic_category_food)
    }

    private fun getMapOfTransactionsByDate(list: List<TransactionEntity>): Map<Long, List<TransactionEntity>> {
        var result = mutableMapOf<Long, List<TransactionEntity>>()
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
        data class ShowData2(val list: List<TransactionCategory>) : Action()
    }
}