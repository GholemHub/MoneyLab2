package com.gholem.moneylab.features.history.navigation

sealed class HistoryNavigationEvent {
    class ToEditTransaction(var pos: Long) : HistoryNavigationEvent()
}