package com.gholem.moneylab.features.add.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.add.AddTransactionFragmentDirections.Companion.actionToCategoryBottomSheetDialog
import javax.inject.Inject

class AddTransactionNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<AddNavigationEvent> {

    override fun navigate(event: AddNavigationEvent) {
        when (event) {
            AddNavigationEvent.ToPreviousScreen -> navControllerWrapper.navigateUp()
            AddNavigationEvent.ToCategoryBottomSheetDialog ->
                navControllerWrapper.navigate(actionToCategoryBottomSheetDialog())
        }
    }
}