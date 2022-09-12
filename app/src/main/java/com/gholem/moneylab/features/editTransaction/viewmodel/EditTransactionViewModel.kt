package com.gholem.moneylab.features.editTransaction.viewmodel


import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.features.chart.ChartFragment
import com.gholem.moneylab.features.createNewCategory.CreateNewCategoryFragment

class EditTransactionViewModel {

    fun getEditItem(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Long>(
            CreateNewCategoryFragment.KEY_CATEGORY_CHOOSE
        )
    }
}