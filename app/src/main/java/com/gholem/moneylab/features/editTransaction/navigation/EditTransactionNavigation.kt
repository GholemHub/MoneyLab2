package com.gholem.moneylab.features.editTransaction.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import com.gholem.moneylab.features.add.AddTransactionFragmentDirections
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import com.gholem.moneylab.features.editTransaction.EditTransactionFragmentDirections
import javax.inject.Inject

class EditTransactionNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<EditTransactionNavigationEvent> {

    override fun navigate(event: EditTransactionNavigationEvent) {
        when (event) {
            EditTransactionNavigationEvent.ToPreviousScreen -> navControllerWrapper.navigateUp()
            EditTransactionNavigationEvent.ToCategoryBottomSheetDialog ->
                navControllerWrapper.navigate(EditTransactionFragmentDirections
                    .actionEditTransactionFragmentToCategoryBottomSheetDialog())
        }
    }
}