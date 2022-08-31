package com.gholem.moneylab.features.authentication.viewmodel

import androidx.lifecycle.ViewModel
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.features.authentication.navigation.AuthenticationNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    val navigation: NavigationLiveData<AuthenticationNavigationEvent> = NavigationLiveData()

    fun goToDashboard() {
        navigation.emit(AuthenticationNavigationEvent.ToDashboard)
        bottomNavigationVisibilityBus.changeVisibility(true)
    }
}