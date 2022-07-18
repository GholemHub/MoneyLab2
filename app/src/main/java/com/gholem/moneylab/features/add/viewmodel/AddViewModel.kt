package com.gholem.moneylab.features.add.viewmodel

import androidx.lifecycle.ViewModel
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.AddNextTransaction
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val bottomNavigationVisibilityBus: BottomNavigationVisibilityBus
) : ViewModel() {

    val navigation: NavigationLiveData<AddNavigationEvent> =
        NavigationLiveData()

    fun init() {
        bottomNavigationVisibilityBus.changeVisibility(false)
    }


    override fun onCleared() {
        bottomNavigationVisibilityBus.changeVisibility(true)
        super.onCleared()
    }

    fun getMockData(): List<AddNextTransaction> = listOf(
        AddNextTransaction.Category(1, "sdf", 1),
        AddNextTransaction.Transaction(10, "01.02.2000"),
        AddNextTransaction.Transaction(120, "01.12.2000"),
        AddNextTransaction.Transaction(42, "02.02.2000"),
        AddNextTransaction.NewTransaction("Add")
    )

}