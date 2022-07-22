package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }

    private var transactionModels: List<TransactionModel> = emptyList()

    fun getTransactions() = viewModelScope.launch {
        transactionModels = getTransactionListUseCase.run(Unit)
    }

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun getMockData(): List<AddTransactionItem> = listOf(
        AddTransactionItem.Category(1, "sd11f", 1),
        AddTransactionItem.Transaction(10, "01.02.2000"),
        AddTransactionItem.NewTransaction
    )

}