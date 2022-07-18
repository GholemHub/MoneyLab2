package com.gholem.moneylab.features.add.navigation

sealed class AddNavigationEvent {
    object ToNextScreen : AddNavigationEvent()
}