package com.gholem.moneylab.features.createNewCategory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.createNewCategory.navigation.CreateNewCategoryNavigationEvent
import com.gholem.moneylab.features.chooseTransactionCategory.domain.InsertCategoryModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewCategoryViewModel @Inject constructor(
    private val insertCategoryModelUseCase: InsertCategoryModelUseCase,
) : ViewModel() {

    private val _actions = Channel<Action>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    var navigation: NavigationLiveData<CreateNewCategoryNavigationEvent> = NavigationLiveData()

    fun navigateToImagePicker() = viewModelScope.launch {
        navigation.emit(CreateNewCategoryNavigationEvent.ToImagePicker)
    }

    fun saveCategoryAndFinish(category: TransactionCategory) = viewModelScope.launch {
        val categoryId = insertCategoryModelUseCase.run(category)
        Action.ReturnCategoryId(categoryId).send()

        navigateToPreviousScreen()
    }

    private fun navigateToPreviousScreen() =
        navigation.emit(CreateNewCategoryNavigationEvent.ToPreviousScreen)

    private fun Action.send() =
        viewModelScope.launch {
            _actions.send(this@send)
        }

    sealed class Action {
        data class ReturnCategoryId(val id: Long) : Action()
    }
}