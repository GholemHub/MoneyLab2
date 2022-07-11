package com.gholem.moneylab.arch.nav

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.navigation.NavOptions

object NavOptionsHelper {

    fun addDefaultsNavOptions(input: NavOptions?): NavOptions =
        buildWithDefaultsOptions(input.orEmpty())

    private fun buildWithDefaultsOptions(input: NavOptions): NavOptions = NavOptions.Builder()
        .apply {
            if (input.shouldLaunchSingleTop()) {
                setLaunchSingleTop(true)
            }
            if (input.popUpToId > -1) {
                setPopUpTo(input.popUpToId, input.isPopUpToInclusive())
            }
            setEnterAnim(input.enterAnim.orAnimation(androidx.navigation.ui.R.anim.nav_default_enter_anim))
            setExitAnim(input.exitAnim.orAnimation(androidx.navigation.ui.R.anim.nav_default_exit_anim))
            setPopEnterAnim(input.popEnterAnim.orAnimation(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim))
            setPopExitAnim(input.popExitAnim.orAnimation(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim))
        }
        .build()

    @AnimRes
    @AnimatorRes
    private fun Int.orAnimation(@AnimRes @AnimatorRes default: Int): Int =
        if (this > -1) this else default

    private fun NavOptions?.orEmpty(): NavOptions =
        this ?: NavOptions.Builder().build()
}