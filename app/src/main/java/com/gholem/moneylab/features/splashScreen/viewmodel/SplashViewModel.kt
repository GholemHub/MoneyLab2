package com.gholem.moneylab.features.splashScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    private val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState: Flow<UiState> = _uiState.receiveAsFlow()

    val navigation: NavigationLiveData<SplashNavigationEvent> = NavigationLiveData()

    fun init() = viewModelScope.launch {
        _uiState.send(UiState.Loading)
    }

    fun goToAuthentication() {
        navigation.emit(SplashNavigationEvent.ToAuthentication)
    }

    fun goToDashboard() {
        navigation.emit(SplashNavigationEvent.ToDashboard)
        bottomNavigationVisibilityBus.changeVisibility(true)
    }

    private suspend fun delay(timeMillis: Long, action: suspend () -> Unit) {
        delay(timeMillis)
        action.invoke()
    }

    sealed class UiState() {

        object Loading : UiState()
        object Loaded : UiState()
        object NavigateToDashboard : UiState()
    }
}