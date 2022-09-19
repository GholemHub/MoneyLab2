package com.gholem.moneylab.features.chart.navigation

sealed class ChartNavigationEvent {
    class ToEditTransaction(var pos: Long) : ChartNavigationEvent()
}