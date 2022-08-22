package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.Transaction
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.InsertTransactionsModelUseCase
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.repository.storage.entity.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//logika zmiana danych
@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val insertTransactionsModelUseCase: InsertTransactionsModelUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val insertCategoryListUseCase: InsertCategoryModelUseCase
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    var listOfCategories = mutableListOf<TransactionCategory>()

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    fun getCategory() = viewModelScope.launch {
        listOfCategories = getCategoryListUseCase.run(Unit) as MutableList<TransactionCategory>
        checkIfCategoriesIsEmpty()
        Action.ShowData(listOfCategories).send()
        Timber.i("List2: ${listOfCategories.size}")
    }



    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
        getCategory()
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

    fun onDoneButtonClick() {
        Action.GetTransactionsData.send()
    }

    fun onTakeCategory() {
        Action.GetCategoryData.send()
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(AddNavigationEvent.ToCategoryBottomSheetDialog)
    }

    fun saveTransaction(transactions: List<Transaction>) = viewModelScope.launch {
        insertTransactionsModelUseCase.run(transactions)
        getTransactionListUseCase.run(Unit).forEach {
            Timber.i("Transaction from db: Category: ${it.categoryId}, amount: ${it.amount}")
        }
        navigation.emit(AddNavigationEvent.ToPreviousScreen)
    }


    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        object GetTransactionsData : Action()
        object GetCategoryData : Action()
        data class ShowData(val list: List<TransactionCategory>) : Action()
    }
}