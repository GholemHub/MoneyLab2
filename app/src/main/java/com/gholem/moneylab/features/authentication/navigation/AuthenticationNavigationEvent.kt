package com.gholem.moneylab.features.authentication.navigation

sealed class AuthenticationNavigationEvent {
    object ToDashboard : AuthenticationNavigationEvent()
}