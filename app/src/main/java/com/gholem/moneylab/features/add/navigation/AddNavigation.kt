package com.gholem.moneylab.features.add.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class AddNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
): NavigationController<AddNavigationEvent> {

    override fun navigate(event: AddNavigationEvent) {
        when(event){

        }
    }
}