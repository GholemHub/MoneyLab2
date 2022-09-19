package com.gholem.moneylab.features.planning.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.planning.PlanningFragmentDirections
import javax.inject.Inject

class PlanningNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<PlanningNavigationEvent> {
    override fun navigate(event: PlanningNavigationEvent) {
        when (event) {
            PlanningNavigationEvent.ToChartScreen -> navControllerWrapper.navigate(
                PlanningFragmentDirections.actionPlanningFragmentToChartNavigation()
            )
        }
    }
}