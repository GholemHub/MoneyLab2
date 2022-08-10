package com.gholem.moneylab.features.authentication.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.authentication.AuthenticationFragmentDirections.Companion.actionAuthenticationFragmentToChartNavigation
import javax.inject.Inject

class AuthenticationNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<AuthenticationNavigationEvent> {
    override fun navigate(event: AuthenticationNavigationEvent) {
        when (event) {
            AuthenticationNavigationEvent.ToDashboard ->
                navControllerWrapper.navigate(actionAuthenticationFragmentToChartNavigation())
        }
    }
}