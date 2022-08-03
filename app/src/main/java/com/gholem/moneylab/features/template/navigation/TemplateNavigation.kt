package com.gholem.moneylab.features.template.navigation

import com.gholem.moneylab.arch.nav.NavControllerWrapper
import com.gholem.moneylab.arch.nav.NavigationController
//import com.gholem.moneylab.features.template.TemplateFragmentDirections.Companion.actionToNextFragment
import com.gholem.moneylab.features.template.navigation.TemplateNavigationEvent.ToErrorScreen
import com.gholem.moneylab.features.template.navigation.TemplateNavigationEvent.ToNextScreen
import com.gholem.moneylab.features.template.navigation.TemplateNavigationEvent.ToPreviousScreen
import javax.inject.Inject

class TemplateNavigation @Inject constructor(
    private val navControllerWrapper: NavControllerWrapper
) : NavigationController<TemplateNavigationEvent> {
    override fun navigate(event: TemplateNavigationEvent) {
        when (event) {
            ToPreviousScreen -> navControllerWrapper.navigateUp()
            ToErrorScreen -> {}
        }
    }
}