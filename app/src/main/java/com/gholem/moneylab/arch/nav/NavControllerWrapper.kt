package com.gholem.moneylab.arch.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class NavControllerWrapper @Inject constructor() {

    private lateinit var fragment: Fragment

    fun init(fragment: Fragment) {
        this.fragment = fragment
    }

    fun navigate(directions: NavDirections) = with(fragment.findNavController()) {
        this.currentDestination?.getAction(directions.actionId)?.let {
            this.navigate(directions, it.navOptions.withDefaults())
        }
    }

    private fun NavOptions?.withDefaults(): NavOptions =
        NavOptionsHelper.addDefaultsNavOptions(this)

    fun navigateUp() {
        fragment.findNavController().navigateUp()
    }

    fun navigateUp(
        @IdRes destinationId: Int,
        inclusive: Boolean
    ) {
        fragment.findNavController().popBackStack(destinationId, inclusive)
    }
}