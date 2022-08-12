package com.gholem.moneylab.features.add.chooseTransactionCategory

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.BottomsheetCategoryFragmentBinding
import com.gholem.moneylab.databinding.ItemBottomSheetCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.navigation.BottomSheetCategoryNavigation
import com.gholem.moneylab.features.add.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetCategoryFragment :
    BaseBottomSheetFragment<BottomsheetCategoryFragmentBinding, BottomSheetCategoryViewModel>() {

    private val viewModel: BottomSheetCategoryViewModel by viewModels()
    lateinit var navigation: BottomSheetCategoryNavigation

    override fun constructViewBinding(): BottomsheetCategoryFragmentBinding =
        BottomsheetCategoryFragmentBinding.inflate(layoutInflater)

    override fun init(viewBinding: BottomsheetCategoryFragmentBinding) {
        viewBinding.createNewCategoryBtn.setOnClickListener {
            
        }
        initCategoryViews(viewBinding)
    }

    private fun navigateToAddTransaction() {
        viewModel.navigateToAddTransaction()
    }

    private fun initCategoryViews(viewBinding: BottomsheetCategoryFragmentBinding) {
        TransactionCategory.values().forEach { category ->
            val categoryViewBinding = ItemBottomSheetCategoryBinding.inflate(
                LayoutInflater.from(context),
                viewBinding.root,
                false
            )
            categoryViewBinding.imageOfCategory.setImageResource(category.image)
            categoryViewBinding.categoryName.text = getString(category.categoryName)

            categoryViewBinding.root.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    KEY_CATEGORY,
                    category.id
                )
                navigateToAddTransaction()
            }

            viewBinding.bottomSheetCategoriesContainer.addView(categoryViewBinding.root)
        }
    }

    override fun setupNavigation() {
        navigation = BottomSheetCategoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    companion object {
        const val KEY_CATEGORY = "KEY_CATEGORY"
    }
}