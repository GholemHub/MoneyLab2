package com.gholem.moneylab.features.chooseTransactionCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.UpdateCategoryItemModelUseCase
import com.gholem.moneylab.features.add.domain.UpdateTransactionModelUseCase
import com.gholem.moneylab.features.chart.adapter.item.ChartItem
import com.gholem.moneylab.features.chooseTransactionCategory.domain.DeleteCategoryItemUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetCategoryViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val deleteCategoryItemUseCase: DeleteCategoryItemUseCase,
    private val updateCategoryItemModelUseCase: UpdateCategoryItemModelUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    private val listOfCategories = mutableListOf<TransactionCategoryModel>()
    val navigation: NavigationLiveData<BottomSheetCategoryEvent> = NavigationLiveData()

    fun getCategories() = viewModelScope.launch {
        listOfCategories.clear()
        listOfCategories.addAll(getCategoryListUseCase.run(Unit) as MutableList<TransactionCategoryModel>)
        Action.ShowData(listOfCategories).send()
    }

    fun updateCategoryList(category: TransactionCategoryModel) = viewModelScope.launch {
        updateCategoryItemModelUseCase.run(category)
    }

    fun navigateToAddTransaction() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToPreviousScreen)
    }

    fun navigateToCreateNewCategory() = viewModelScope.launch {
        navigation.emit(BottomSheetCategoryEvent.ToCreateNewCategory)
    }

    fun deleteCategory(categoryId: Int) = viewModelScope.launch {
        deleteCategoryItemUseCase.run(categoryId)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowData(val list: List<TransactionCategoryModel>) : Action()
    }
}