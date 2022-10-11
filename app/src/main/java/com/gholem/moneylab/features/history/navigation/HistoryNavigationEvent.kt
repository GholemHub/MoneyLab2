package com.gholem.moneylab.features.history.navigation

sealed class HistoryNavigationEvent {
    class ToEditTransaction(var position: Long) : HistoryNavigationEvent()
}