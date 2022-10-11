package com.gholem.moneylab.features.history.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.history.HistoryFragmentDirections.Companion.actionHistoryFragmentToEditTransactionFragment
import javax.inject.Inject

class HistoryNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<HistoryNavigationEvent> {

    override fun navigate(event: HistoryNavigationEvent) {

        when (event) {
            is HistoryNavigationEvent.ToEditTransaction -> {
                navControllerWrapper.navigate(actionHistoryFragmentToEditTransactionFragment(event.position))
            }
        }
    }
}