package com.gholem.moneylab.features.editTransaction.viewmodel


import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.domain.model.TransactionModel
import com.gholem.moneylab.features.add.domain.DeleteTransactionModelUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionItemUseCase
import com.gholem.moneylab.features.add.domain.GetTransactionListUseCase
import com.gholem.moneylab.features.add.domain.UpdateTransactionModelUseCase
import com.gholem.moneylab.features.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.editTransaction.navigation.EditTransactionNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getTransactionItemUseCase: GetTransactionItemUseCase,
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus,
    private val updateTransactionModelUseCase: UpdateTransactionModelUseCase,
    private val deleteTransactionModelUseCase: DeleteTransactionModelUseCase
    ) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()
    val navigation: NavigationLiveData<EditTransactionNavigationEvent> =
        NavigationLiveData()
    private lateinit var currentTransaction: TransactionModel

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
        val transaction = getTransactionItemUseCase.run(_positionItem)

        setCurrentTransaction(transaction)
        Action.GetCurrentTransaction(getCurrentTransaction()).send()
    }

    fun getCurrentTransaction() = currentTransaction

    fun setCurrentTransaction(_currentTransaction: TransactionModel){
        this.currentTransaction = _currentTransaction
    }

    fun deleteTransaction() = viewModelScope.launch {
        deleteTransactionModelUseCase.run(getCurrentTransaction().transactionId.toInt())
        navigation.emit(EditTransactionNavigationEvent.ToPreviousScreen)
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
        updateTransactionModelUseCase.run(getCurrentTransaction())
        navigation.emit(EditTransactionNavigationEvent.ToPreviousScreen)
    }

    sealed class Action {
        data class GetCurrentTransaction(val transaction: TransactionModel) : Action()
        data class GetCurrentCategory(val category: TransactionCategoryModel) : Action()
        object SetEditedTransaction : Action()
    }
}