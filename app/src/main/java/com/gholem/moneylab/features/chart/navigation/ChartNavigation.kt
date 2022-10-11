package com.gholem.moneylab.features.chart.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.chart.ChartFragmentDirections
import javax.inject.Inject

class ChartNavigation @Inject constructor(
        private val navControllerWrapper: NavControllerWrapper
) : NavigationController<ChartNavigationEvent> {
    override fun navigate(event: ChartNavigationEvent) {
        when (event) {
            ChartNavigationEvent.ToChartScreen -> navControllerWrapper.navigate(
                    ChartFragmentDirections.actionChartFragmentToHistoryNavigation()
            )
        }
    }
}