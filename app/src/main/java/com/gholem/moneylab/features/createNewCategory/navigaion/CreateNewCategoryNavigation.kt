package com.gholem.moneylab.features.createNewCategory.navigaion

import com.gholem.moneylab.R
import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class CreateNewCategoryNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<CreateNewCategoryEvent> {
    override fun navigate(event: CreateNewCategoryEvent) {
        when (event) {
            CreateNewCategoryEvent.ToPreviousScreen ->
                navControllerWrapper.navigateUp()
            CreateNewCategoryEvent.ToImagePicker ->
                navControllerWrapper.navigate(R.id.action_createNewCategoryFragment_to_createNewTransactionCategoryFragment)
        }
    }
}
