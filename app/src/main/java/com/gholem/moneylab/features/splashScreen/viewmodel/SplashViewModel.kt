package com.gholem.moneylab.features.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()

    val navigation: NavigationLiveData<SplashNavigationEvent> = NavigationLiveData()

    fun init() = viewModelScope.launch {
        _uiState.send(UiState.Loading)
        delay(3000)
        _uiState.send(UiState.Loaded)
        delay(1000)
        _uiState.send(UiState.NavigateToNext)
    }

    fun goToNexScreen() {
        navigation.emit(SplashNavigationEvent.ToNextScreen)
    }

    sealed class UiState() {

        object Loading : UiState()
        object Loaded : UiState()
        object NavigateToNext : UiState()
    }
}