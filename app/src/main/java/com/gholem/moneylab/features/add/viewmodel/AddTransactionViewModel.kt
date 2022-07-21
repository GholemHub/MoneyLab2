package com.gholem.moneylab.features.add.viewmodel

  import androidx.lifecycle.ViewModel
import com.gholem.moneylab.arch.nav.NavigationLiveData
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.domain.model.AddTransactionItem
import com.gholem.moneylab.features.add.navigation.AddNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
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

    fun getMockData(): List<AddTransactionItem> = listOf(
        AddTransactionItem.Category(1, "sd11f", 1),
        AddTransactionItem.Transaction(10, "01.02.2000"),
        AddTransactionItem.NewTransaction
    )

}