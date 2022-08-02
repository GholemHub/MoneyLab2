package com.gholem.moneylab.features.add

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.BottomsheetCategoryFragmentBinding
import com.gholem.moneylab.databinding.ItemBottomSheetCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.navigation.BottomSheetCategoryNavigation
import com.gholem.moneylab.features.add.viewmodel.BottomSheetCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetCategoryFragment :
    BaseBottomSheetFragment<BottomsheetCategoryFragmentBinding, BottomSheetCategoryViewModel>() {

    private val viewModel: BottomSheetCategoryViewModel by viewModels()

    override fun constructViewBinding(): BottomsheetCategoryFragmentBinding =
        BottomsheetCategoryFragmentBinding.inflate(layoutInflater)

    override fun init(viewBinding: BottomsheetCategoryFragmentBinding) {
        initCategoryViews(viewBinding)
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
                //navigation by event
                dismiss()
            }

            viewBinding.bottomSheetCategoriesContainer.addView(categoryViewBinding.root)
        }
    }

    lateinit var bottomSheetCategoryNavigation: BottomSheetCategoryNavigation

    override fun setupNavigation() {
        bottomSheetCategoryNavigation = BottomSheetCategoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, bottomSheetCategoryNavigation::navigate)
    }

    companion object {
        const val KEY_CATEGORY = "KEY_CATEGORY"
    }

}