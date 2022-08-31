package com.gholem.moneylab.features.createNewCategory.navigation

import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class CreateNewCategoryNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<CreateNewCategoryNavigationEvent> {
    override fun navigate(event: CreateNewCategoryNavigationEvent) {
        when (event) {
            CreateNewCategoryNavigationEvent.ToPreviousScreen ->
                navControllerWrapper.navigateUp()
            CreateNewCategoryNavigationEvent.ToImagePicker ->
                navControllerWrapper.navigate(R.id.action_createNewCategoryFragment_to_createNewTransactionCategoryFragment)
        }
    }
}
