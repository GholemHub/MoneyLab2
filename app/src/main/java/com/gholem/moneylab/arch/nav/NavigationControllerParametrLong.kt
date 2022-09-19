package com.gholem.moneylab.arch.nav

import androidx.navigation.NavDirections

interface NavigationControllerParametrLong <T> {

    fun navigate(event: T, parametr: Long)

}