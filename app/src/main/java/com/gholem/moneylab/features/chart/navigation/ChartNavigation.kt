package com.gholem.moneylab.features.chart.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.chart.ChartFragmentDirections.Companion.actionChartFragmentToEditTransactionFragment
import javax.inject.Inject

class ChartNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<ChartNavigationEvent> {
    override fun navigate(event: ChartNavigationEvent) {
        when (event) {
            ChartNavigationEvent.ToEditTransaction ->
                navControllerWrapper.navigate(actionChartFragmentToEditTransactionFragment())
        }
    }
}