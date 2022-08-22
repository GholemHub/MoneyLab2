package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.navigaion.CreateNewCategoryEvent
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.GetCategoryListUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import com.gholem.moneylab.features.add.chooseTransactionCategory.navigation.BottomSheetCategoryEvent
import com.gholem.moneylab.features.add.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import com.gholem.moneylab.features.add.viewmodel.AddTransactionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateNewCategoryViewModel  @Inject constructor(

    private val insertCategoryModelUseCase: InsertCategoryModelUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase

) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    val navigation: NavigationLiveData<CreateNewCategoryEvent> = NavigationLiveData()

    fun navigateToImagePicker() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryEvent.ToImagePicker)
        getCategoryListUseCase.run(Unit).forEach {
            Timber.i("Transaction from db: Category: ${it.categoryName}")
        }
    }

    fun synchronizeId() =  viewModelScope.launch {
        Action.ShowData(getCategoryListUseCase.run(Unit)).send()
    }

    fun saveCategory(category: TransactionCategory) = viewModelScope.launch {
        insertCategoryModelUseCase.run(category)
    }

    fun onDoneButtonClick() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryEvent.ToPreviousScreen)
    }

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ShowData(val list: List<TransactionCategory>) : Action()
    }
}