package com.gholem.moneylab.arch.nav

interface NavigationController<T> {

    fun navigate(event: T)
}