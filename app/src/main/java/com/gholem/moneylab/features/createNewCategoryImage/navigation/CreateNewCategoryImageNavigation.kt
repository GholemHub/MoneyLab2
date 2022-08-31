package com.gholem.moneylab.features.createNewCategoryImage.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class CreateNewCategoryImageNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<CreateNewCategoryImageNavigationEvent> {
    override fun navigate(event: CreateNewCategoryImageNavigationEvent) {

        when (event) {
            CreateNewCategoryImageNavigationEvent.ToPreviousScreen ->{
                navControllerWrapper.navigateUp()
            }
        }
    }
}