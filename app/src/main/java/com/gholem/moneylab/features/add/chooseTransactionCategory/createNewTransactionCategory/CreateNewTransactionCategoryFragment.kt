package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewTransactionCategory

import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.FragmentCreateNewCategoryBinding
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewTransactionCategory.viewmodel.CreateNewTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CreateNewTransactionCategoryFragment :
    BaseBottomSheetFragment<FragmentCreateNewCategoryBinding, CreateNewTransactionViewModel>() {
    override fun constructViewBinding(): FragmentCreateNewCategoryBinding =
        FragmentCreateNewCategoryBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentCreateNewCategoryBinding) {

    }

    override fun setupNavigation() {

    }
}