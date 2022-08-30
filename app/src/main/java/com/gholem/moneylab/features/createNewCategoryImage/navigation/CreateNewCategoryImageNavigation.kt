package com.gholem.moneylab.features.createNewCategoryImage.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class CreateNewCategoryImageNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<CreateNewCategoryImageEvent> {
    override fun navigate(event: CreateNewCategoryImageEvent) {
        when (event) {
            CreateNewCategoryImageEvent.ToPreviousScreen ->{
                navControllerWrapper.navigateUp()
            }
        }
    }
}