package com.gholem.moneylab.features.chart.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationControllerParametrLong
import com.gholem.moneylab.features.chart.ChartFragmentDirections.Companion.actionChartFragmentToEditTransactionFragment
import timber.log.Timber.i
import javax.inject.Inject

class ChartNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationControllerParametrLong<ChartNavigationEvent> {

    override fun navigate(event: ChartNavigationEvent, pos: Long) {

        when (event) {
            is ChartNavigationEvent.ToEditTransaction -> {
                navControllerWrapper.navigate(actionChartFragmentToEditTransactionFragment(pos))
            }
        }
    }
}