package com.gholem.moneylab.features.chooseTransactionCategory.navigation

import com.gholem.moneylab.R
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
            BottomSheetCategoryEvent.ToCreateNewCategory ->
                navControllerWrapper.navigate(R.id.action_category_bottom_sheet_dialog_to_createNewCategoryFragment)
        }
    }
}