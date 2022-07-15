package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    fun init() {

        bottomNavigationVisibilityBus.changeVisibility(false)
    }


    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

}