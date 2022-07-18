package com.gholem.moneylab.features.template.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.domain.model.TemplateModel
import com.gholem.moneylab.features.template.domain.GetTemplateListUseCase
import com.gholem.moneylab.features.template.domain.InsertTemplateModelUseCase
import com.gholem.moneylab.features.template.navigation.TemplateNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val getTemplateListUseCase: GetTemplateListUseCase,
    private val insertTemplateModelUseCase: InsertTemplateModelUseCase,
) : ViewModel() {

    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()

    val navigation: NavigationLiveData<TemplateNavigationEvent> = NavigationLiveData()

    private var templateModels: List<TemplateModel> = emptyList()

    fun getTemplates() = viewModelScope.launch {
        templateModels = getTemplateListUseCase.run(Unit)
    }

    fun saveTemplateModel(templateModel: TemplateModel) = viewModelScope.launch {
        insertTemplateModelUseCase.run(templateModel)
    }

    fun onContinueClick() {
        navigation.emit(TemplateNavigationEvent.ToNextScreen)
    }

    fun onBackClick() {
        navigation.emit(TemplateNavigationEvent.ToPreviousScreen)
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Loaded(val templateModelList: List<TemplateModel>) : UiState()
    }
}