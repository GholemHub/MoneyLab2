package com.gholem.moneylab.arch.nav

import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class NavControllerWrapper @Inject constructor() {

    private lateinit var fragment: Fragment
    private lateinit var dialog: Dialog

    fun init(fragment: Fragment) {
        this.fragment = fragment
    }

    fun init(dialog: Dialog) {
        this.dialog = dialog
    }

    fun navigate(directions: NavDirections) = with(fragment.findNavController()) {
        this.currentDestination?.getAction(directions.actionId)?.let {
            this.navigate(directions, it.navOptions.withDefaults())
        }
    }

    private fun NavOptions?.withDefaults(): NavOptions =
        NavOptionsHelper.addDefaultsNavOptions(this)

    fun navigate(resId: Int) {
        fragment.findNavController().navigate(resId)
    }

    fun navigateUp() {
        fragment.findNavController().navigateUp()
    }
}