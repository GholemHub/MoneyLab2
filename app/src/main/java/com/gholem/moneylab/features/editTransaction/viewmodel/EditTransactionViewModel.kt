package com.gholem.moneylab.features.editTransaction.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
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
    private val getTransactionListUseCase: GetTransactionListUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val updateTransactionModelUseCase: UpdateTransactionModelUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    val navigation: NavigationLiveData<EditTransactionNavigationEvent> =
        NavigationLiveData()
    lateinit var currentTransaction: TransactionModel
    private var currentTransactionPosition: Long = 0

    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }

    fun getTransactionInfo(_positionItem: Long) = viewModelScope.launch {
        val transactions = getTransactionListUseCase.run(Unit)
        currentTransactionPosition = _positionItem
        currentTransaction = TransactionModel(
            transactions[currentTransactionPosition.toInt()].category,
            transactions[currentTransactionPosition.toInt()].amount,
            transactions[currentTransactionPosition.toInt()].date
        )
        Action.GetCurrentTransaction(currentTransaction).send()
    }

    fun navigateToCategoryBottomSheet() = viewModelScope.launch {
        navigation.emit(EditTransactionNavigationEvent.ToCategoryBottomSheetDialog)
    }

    fun setIdOfCategory(result: Long) = viewModelScope.launch {
        val categories = getCategoryListUseCase.run(Unit)
        Action.GetCurrentCategory(categories[result.toInt()]).send()
    }

    fun onDoneButtonClick() {
        Action.SetEditedTransaction.send()
    }

    fun saveEditedTransaction() = viewModelScope.launch {
        updateTransactionModelUseCase.BiConsumer(currentTransaction, currentTransactionPosition + 1)
        navigation.emit(EditTransactionNavigationEvent.ToPreviousScreen)
    }

    sealed class Action {
        data class GetCurrentTransaction(val transaction: TransactionModel) : Action()
        data class GetCurrentCategory(val category: TransactionCategoryModel) : Action()
        object SetEditedTransaction : Action()
    }
}