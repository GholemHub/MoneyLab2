package com.gholem.moneylab.features.splashScreen.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.splashScreen.SplashFragmentDirections.Companion.actionSplashFragmentToAuthenticationFragment
import com.gholem.moneylab.features.splashScreen.SplashFragmentDirections.Companion.actionToDashboard
import javax.inject.Inject

class SplashNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<SplashNavigationEvent> {

    override fun navigate(event: SplashNavigationEvent) {
        when (event) {
            SplashNavigationEvent.ToDashboard -> navControllerWrapper.navigate(actionToDashboard())
            SplashNavigationEvent.ToAuthentication -> navControllerWrapper.navigate(
                actionSplashFragmentToAuthenticationFragment())
        }
    }
}