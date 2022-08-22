package com.gholem.moneylab.features.add.chooseTransactionCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber.i
import javax.inject.Inject

@HiltViewModel
class BottomSheetCategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val insertCategoryListUseCase: InsertCategoryModelUseCase
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    var listOfCategories = mutableListOf<TransactionCategory>()

    fun getCategory() = viewModelScope.launch {
        listOfCategories = getCategoryListUseCase.run(Unit) as MutableList<TransactionCategory>
        checkIfCategoriesIsEmpty()
        Action.ShowData(listOfCategories).send()
        i("List2: ${listOfCategories.size}")
    }

    private fun checkIfCategoriesIsEmpty() {
        if (listOfCategories.size == 0) {
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
            saveCategories(listOfCategories)
        }
    }

    fun saveCategories(caregories: List<TransactionCategory>) = viewModelScope.launch {
        caregories.forEach {
            insertCategoryListUseCase.run(it)
        }

    }

    fun getCategoryFromRoom() = viewModelScope.launch {
        getCategory()
    }

    val navigation: NavigationLiveData<BottomSheetCategoryEvent> = NavigationLiveData()

    fun navigateToAddTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToPreviousScreen)
    }

    fun navigateToCreateNewTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToCreateNewCategory)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowData(val list: List<TransactionCategory>) : Action()
    }
}