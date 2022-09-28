package com.gholem.moneylab.features.chart.navigation

sealed class ChartNavigationEvent {
    object ToChartScreen : ChartNavigationEvent()
}