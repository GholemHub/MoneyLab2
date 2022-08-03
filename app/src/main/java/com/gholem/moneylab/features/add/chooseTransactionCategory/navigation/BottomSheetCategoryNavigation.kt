package com.gholem.moneylab.features.add.chooseTransactionCategory.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import javax.inject.Inject

class BottomSheetCategoryNavigation @Inject constructor (
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<BottomSheetCategoryEvent>{
    override fun navigate(event: BottomSheetCategoryEvent) {
        when(event){
            BottomSheetCategoryEvent.ToPreviousScreen ->
                navControllerWrapper.navigateUp()
        }
    }
}