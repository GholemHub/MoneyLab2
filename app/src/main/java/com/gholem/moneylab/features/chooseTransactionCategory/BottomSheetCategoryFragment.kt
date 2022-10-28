package com.gholem.moneylab.features.chooseTransactionCategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.BottomsheetCategoryFragmentBinding
import com.gholem.moneylab.databinding.ItemBottomSheetCategoryBinding
import com.gholem.moneylab.domain.model.CategoryItem
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.domain.model.CategoryItem.IncomeCategoryModel
import com.gholem.moneylab.features.chooseTransactionCategory.navigation.BottomSheetCategoryNavigation
import com.gholem.moneylab.features.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetCategoryFragment :
    BaseBottomSheetFragment<BottomsheetCategoryFragmentBinding, BottomSheetCategoryViewModel>() {

    private val viewModel: BottomSheetCategoryViewModel by viewModels()
    lateinit var navigation: BottomSheetCategoryNavigation

    override fun constructViewBinding(): BottomsheetCategoryFragmentBinding =
        BottomsheetCategoryFragmentBinding.inflate(layoutInflater)

    override fun init(viewBinding: BottomsheetCategoryFragmentBinding) {
        viewModel.getCategories()
        observeActions()

        viewBinding.createNewCategoryBtn.setOnClickListener {
            navigateToCreateNewTransaction()
        }
    }

    private fun navigateToAddTransaction() {
        viewModel.navigateToAddTransaction()
    }

    private fun navigateToCreateNewTransaction() {
        viewModel.navigateToCreateNewCategory()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is BottomSheetCategoryViewModel.Action.ShowData -> {
                    val firstCategory: CategoryItem = action.list.first()
                    action.list.forEachIndexed { index, category ->
                        if (category is ExpenseCategoryModel) {
                            val categoryViewBinding = ItemBottomSheetCategoryBinding.inflate(
                                LayoutInflater.from(context),
                                getViewBinding().root,
                                false
                            )
                            categoryViewBinding.imageOfCategory.setImageResource(category.image)
                            categoryViewBinding.imageOfCategory.setColorFilter(resources.getColor(R.color.primary_color_dark))
                            categoryViewBinding.categoryName.text = category.categoryName

                            if (index == 0) {
                                categoryViewBinding.deleteCategory.visibility = View.GONE
                            }

                            categoryViewBinding.root.setOnClickListener {
                                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                                    KEY_CATEGORY,
                                    category.id
                                )
                                navigateToAddTransaction()
                            }
                            categoryViewBinding.deleteCategory.setOnClickListener {

                                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                                    KEY_CATEGORY,
                                    when (firstCategory) {
                                        is ExpenseCategoryModel -> {
                                            firstCategory.id
                                        }
                                        is IncomeCategoryModel -> {
                                            firstCategory.id
                                        }
                                    }

                                )

                                var categoryItem = action.list.first {
                                    when (it) {
                                        is ExpenseCategoryModel -> {
                                            it.id == category.id
                                        }
                                        is IncomeCategoryModel -> {
                                            it.id == category.id
                                        }
                                    }
                                }
                                when (categoryItem) {
                                    is ExpenseCategoryModel -> {
                                        categoryItem.id?.let { categoryToDelete ->

                                            viewModel.deleteCategory(
                                                categoryToDelete.toInt()
                                            )
                                            categoryItem = firstCategory
                                            viewModel.updateCategoryList(categoryItem as ExpenseCategoryModel)
                                            viewModel.getCategories()
                                        }
                                    }
                                    is IncomeCategoryModel -> {
                                        (categoryItem as IncomeCategoryModel).id?.let { categoryToDelete ->

                                            viewModel.deleteCategory(
                                                categoryToDelete.toInt()
                                            )
                                            categoryItem = firstCategory
                                            viewModel.updateCategoryList(categoryItem as IncomeCategoryModel)
                                            viewModel.getCategories()
                                        }
                                    }
                                }


                                navigateToAddTransaction()
                            }
                            getViewBinding().bottomSheetCategoriesContainer.addView(
                                categoryViewBinding.root
                            )
                        }
                    }
                    action.list.forEachIndexed { index, category ->
                        if (category is IncomeCategoryModel) {
                            val categoryViewBinding = ItemBottomSheetCategoryBinding.inflate(
                                LayoutInflater.from(context),
                                getViewBinding().root,
                                false
                            )
                            categoryViewBinding.imageOfCategory.setImageResource(category.image)
                            categoryViewBinding.imageOfCategory.setColorFilter(resources.getColor(R.color.primary_green))
                            categoryViewBinding.categoryName.text = category.categoryName

                            if (index == 0) {
                                categoryViewBinding.deleteCategory.visibility = View.GONE
                            }

                            categoryViewBinding.root.setOnClickListener {
                                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                                    KEY_CATEGORY,
                                    category.id
                                )
                                navigateToAddTransaction()
                            }
                            categoryViewBinding.deleteCategory.setOnClickListener {

                                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                                    KEY_CATEGORY,
                                    when (firstCategory) {
                                        is ExpenseCategoryModel -> {
                                            firstCategory.id
                                        }
                                        is IncomeCategoryModel -> {
                                            firstCategory.id
                                        }
                                    }

                                )



                                var categoryItem = action.list.first {
                                    when (it) {
                                        is ExpenseCategoryModel -> {
                                            it.id == category.id
                                        }
                                        is IncomeCategoryModel -> {
                                            it.id == category.id
                                        }
                                    }
                                }
                                when (categoryItem) {
                                    is ExpenseCategoryModel -> {
                                        (categoryItem as ExpenseCategoryModel).id?.let { categoryToDelete ->

                                            viewModel.deleteCategory(
                                                categoryToDelete.toInt()
                                            )
                                            categoryItem = firstCategory
                                            viewModel.updateCategoryList(categoryItem as ExpenseCategoryModel)
                                            viewModel.getCategories()
                                        }
                                    }
                                    is IncomeCategoryModel -> {
                                        (categoryItem as IncomeCategoryModel).id?.let { categoryToDelete ->

                                            viewModel.deleteCategory(
                                                categoryToDelete.toInt()
                                            )
                                            categoryItem = firstCategory
                                            viewModel.updateCategoryList(categoryItem as IncomeCategoryModel)
                                            viewModel.getCategories()
                                        }
                                    }
                                }
                                navigateToAddTransaction()
                            }
                            getViewBinding().bottomSheetCategoriesContainer.addView(
                                categoryViewBinding.root
                            )
                        }
                    }
                }
            }
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