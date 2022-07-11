package com.gholem.moneylab.features.splashScreen.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.splashScreen.SplashFragmentDirections.Companion.actionToTemplateFragment
import javax.inject.Inject

class SplashNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
): NavigationController<SplashNavigationEvent>{
    override fun navigate(event: SplashNavigationEvent) {
        when(event){
            SplashNavigationEvent.ToNextScreen -> navControllerWrapper.navigate(actionToTemplateFragment())
        }
    }
}