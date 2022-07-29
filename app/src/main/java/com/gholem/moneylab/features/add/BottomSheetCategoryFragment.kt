package com.gholem.moneylab.features.add

import android.view.LayoutInflater
import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.BottomsheetCategoryFragmentBinding
import com.gholem.moneylab.databinding.ItemBottomSheetCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.viewmodel.BottomSheetCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BottomSheetCategoryFragment :
    BaseBottomSheetFragment<BottomsheetCategoryFragmentBinding, BottomSheetCategoryViewModel>() {

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
                Timber.i("${getString(category.categoryName)}")
            }

            viewBinding.bottomSheetCategoriesContainer.addView(categoryViewBinding.root)
        }
    }

    override fun setupNavigation() {
    }
}