package com.gholem.moneylab.features.planning.navigation

import com.gholem.moneylab.features.add.navigation.AddNavigationEvent

sealed class PlanningNavigationEvent {
    object ToChartScreen : PlanningNavigationEvent()
}