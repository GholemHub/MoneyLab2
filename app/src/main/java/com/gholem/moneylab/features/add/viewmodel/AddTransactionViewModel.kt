package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val insertTransactionModelUseCase: InsertTransactionModelUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    val actions: NavigationLiveData<Action> =
        NavigationLiveData()

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }

    fun saveTransaction(transaction: List<Transaction>) = viewModelScope.launch {
        insertTransactionModelUseCase.run(transaction[0])
        getTransactionListUseCase.run(Unit).forEach {
            Timber.i("Transaction from db: Category: ${it.category.categoryName}, amount: ${it.amount}")
        }
    }

    fun onDoneButtonClick() {
        actions.emit(Action.GetData)
    }

    sealed class Action {

        object GetData: Action()
    }
}