package com.gholem.moneylab.features.editTransaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.DeleteTransactionModelUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionItemUseCase
import com.gholem.moneylab.features.add.domain.UpdateTransactionModelUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getTransactionItemUseCase: GetTransactionItemUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val updateTransactionModelUseCase: UpdateTransactionModelUseCase,
    private val deleteTransactionModelUseCase: DeleteTransactionModelUseCase
) : ViewModel() {

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    val navigation: NavigationLiveData<EditTransactionNavigationEvent> =
        NavigationLiveData()
    private lateinit var currentTransaction: TransactionModel

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }

    fun getTransactionInfo(positionItem: Long) = viewModelScope.launch {
        val transaction = getTransactionItemUseCase.run(positionItem)
        setCurrentTransaction(transaction)
        Action.UpdateTransactionInfo(currentTransaction).send()
    }

    fun getTransactionDate() = currentTransaction.date

    fun changeTransactionAmount(amount: Int) {
        currentTransaction = currentTransaction.copy(amount = amount)
    }

    fun changeTransactionDate(date: Long) {
        currentTransaction = currentTransaction.copy(date = date)
    }

    fun changeTransactionCategory(category: CategoryItem) {
        currentTransaction = currentTransaction.copy(category = category)
    }

    fun setCurrentTransaction(currentTransaction: TransactionModel) {
        this.currentTransaction = currentTransaction
    }

    fun deleteTransaction() = viewModelScope.launch {
        deleteTransactionModelUseCase.run(currentTransaction.transactionId.toInt())
        navigation.emit(EditTransactionNavigationEvent.ToPreviousScreen)
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(EditTransactionNavigationEvent.ToCategoryBottomSheetDialog)
    }

    fun setIdOfCategory(result: Long) = viewModelScope.launch {
        val categories = getCategoryListUseCase.run(Unit)
        //it.id == result

        var categoryItem = categories.first{
            when (it){
                is ExpenseCategoryModel -> {
                    it.id == result
                }
                is CategoryItem.IncomeCategoryModel -> {
                    it.id == result
                }
            }
        }

        Action.GetCurrentCategory(categoryItem).send()

    }

    fun onDoneButtonClick(amount: String) {
        if (amount.isNotBlank()) {
            Action.SetEditedTransaction.send()
        }
    }

    fun saveEditedTransaction() = viewModelScope.launch {
        updateTransactionModelUseCase.run(currentTransaction)
        navigation.emit(EditTransactionNavigationEvent.ToPreviousScreen)
    }

    sealed class Action {
        data class UpdateTransactionInfo(val transaction: TransactionModel) : Action()
        data class GetCurrentCategory(val category: CategoryItem) : Action()
        object SetEditedTransaction : Action()
    }
}