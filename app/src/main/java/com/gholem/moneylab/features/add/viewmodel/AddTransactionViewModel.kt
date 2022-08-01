package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val insertTransactionsModelUseCase: InsertTransactionsModelUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }

    fun onDoneButtonClick() {
        Action.GetTransactionsData.send()
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(AddNavigationEvent.ToCategoryBottomSheetDialog)
    }

    fun saveTransaction(transactions: List<Transaction>) = viewModelScope.launch {
        insertTransactionsModelUseCase.run(transactions)
        getTransactionListUseCase.run(Unit).forEach {
            Timber.i("Transaction from db: Category: ${it.category.name}, amount: ${it.amount}")
        }
        navigation.emit(AddNavigationEvent.ToPreviousScreen)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        object GetTransactionsData : Action()
    }
}