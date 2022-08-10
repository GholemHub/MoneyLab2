package com.gholem.moneylab.features.splashScreen.navigation

sealed class SplashNavigationEvent {

    object ToDashboard : SplashNavigationEvent()
    object ToAuthentication : SplashNavigationEvent()
}