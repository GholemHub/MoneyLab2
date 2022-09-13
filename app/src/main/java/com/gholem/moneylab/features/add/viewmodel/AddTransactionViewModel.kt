package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.adapter.item.AddTransactionItem
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.i
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionsModelUseCase: InsertTransactionsModelUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val getCategoryListUseCase: GetCategoryListUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    val adapterData = AddTransactionItem.getDefaultItems().toMutableList()

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
        updateCategories()
    }

    fun updateList(idCategory: Long) = viewModelScope.launch {
        fetchCategories()
        Action.SelectCategory(idCategory).send()
    }

    fun onDoneButtonClick() {
        Action.GetTransactionsData.send()
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(AddNavigationEvent.ToCategoryBottomSheetDialog)
    }

    fun saveTransaction(transactions: List<TransactionModel>) = viewModelScope.launch {
        val listOfEmpty = checkTransactionAmount()
        if (listOfEmpty.isEmpty()) {
            insertTransactionsModelUseCase.run(transactions)
            navigation.emit(AddNavigationEvent.ToPreviousScreen)
        } else {
            Action.InvalidData(listOfEmpty).send()
        }
    }

    private fun updateCategories() = viewModelScope.launch {
        fetchCategories()
    }

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    private suspend fun fetchCategories() {
        val listOfCategories = getCategoryListUseCase.run(Unit)
        Action.ShowData(listOfCategories).send()
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    private fun checkTransactionAmount(): List<Int> {
        val listOfEmpty = mutableListOf<Int>()
        Timber.i("DDD ViewModel: ${adapterData}")
        adapterData.forEachIndexed { position, transactionItem ->
            if (transactionItem is AddTransactionItem.Transaction &&
                transactionItem.amount.isBlank()
            ) {
                listOfEmpty.add(position)
            }
        }

        return listOfEmpty
    }


    sealed class Action {
        object GetTransactionsData : Action()
        data class ShowData(val list: List<TransactionCategoryModel>) : Action()
        data class SelectCategory(val categoryId: Long) : Action()
        data class InvalidData(val listOfIndexes: List<Int>) : Action()
    }
}