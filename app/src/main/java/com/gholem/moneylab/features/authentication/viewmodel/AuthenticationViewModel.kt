package com.gholem.moneylab.features.authentication.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.features.authentication.navigation.AuthenticationNavigationEvent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            goToDashboard()
        } catch (e: ApiException) {
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    companion object {
        var RC_SIGN_IN = 0
    }
}