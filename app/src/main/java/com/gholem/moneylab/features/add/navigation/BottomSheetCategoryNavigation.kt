package com.gholem.moneylab.features.add.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
import timber.log.Timber.i
import javax.inject.Inject

class BottomSheetCategoryNavigation @Inject constructor (
    private val navControllerWrapper: NavControllerWrapper<Any?>
) : NavigationController<BottomSheetCategoryEvent>{
    override fun navigate(event: BottomSheetCategoryEvent) {

        when(event){

            BottomSheetCategoryEvent.ToPreviousScreen ->
                navControllerWrapper.navigateUp()
        }

    }

}