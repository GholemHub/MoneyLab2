package com.gholem.moneylab.features.chooseTransactionCategory

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseBottomSheetFragment
import com.gholem.moneylab.databinding.BottomsheetCategoryFragmentBinding
import com.gholem.moneylab.databinding.ItemBottomSheetCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategoryModel
import com.gholem.moneylab.features.chooseTransactionCategory.navigation.BottomSheetCategoryNavigation
import com.gholem.moneylab.features.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.i

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
                    val firstCategory: TransactionCategoryModel = action.list.first()
                    action.list.forEachIndexed { index, category ->
                        val categoryViewBinding = ItemBottomSheetCategoryBinding.inflate(
                            LayoutInflater.from(context),
                            getViewBinding().root,
                            false
                        )
                        categoryViewBinding.imageOfCategory.setImageResource(category.image)
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
                                firstCategory.id
                            )
                            val categoryItem = action.list.first { it.id == category.id }
                            categoryItem.id?.let { categoryToDelete ->
                                viewModel.deleteCategory(
                                    categoryToDelete.toInt()
                                )
                                viewModel.getCategories()
                            }

                            navigateToAddTransaction()
                        }
                        getViewBinding().bottomSheetCategoriesContainer.addView(categoryViewBinding.root)
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