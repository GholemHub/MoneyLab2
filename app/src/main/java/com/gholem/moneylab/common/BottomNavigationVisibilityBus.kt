package com.gholem.moneylab.common

import com.gholem.moneylab.arch.nav.NavigationLiveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class BottomNavigationVisibilityBus @Inject constructor() {

    val isVisible = NavigationLiveData<Boolean>()

    fun changeVisibility(isVisible: Boolean) {
        this.isVisible.emit(isVisible)
    }
}