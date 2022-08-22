package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentNewCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.navigaion.CreateNewCategoryNavigation
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.viewmodel.CreateNewCategoryViewModel
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategoryImage.CreateNewCategoryImageFragment.Companion.KEY_IMAGE
import com.gholem.moneylab.features.add.chooseTransactionCategory.viewmodel.BottomSheetCategoryViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import timber.log.Timber.i

@AndroidEntryPoint
class CreateNewCategoryFragment :
    BaseFragment<FragmentNewCategoryBinding, CreateNewCategoryViewModel>() {
    lateinit var categoryName: String
    var categoryImageResource: Int = 0
    private val viewModel: CreateNewCategoryViewModel by viewModels()
    lateinit var navigation: CreateNewCategoryNavigation
    var idd = 0

    private fun observeCategoryChange(viewBinding: FragmentNewCategoryBinding) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            KEY_IMAGE
        )
            ?.observe(viewLifecycleOwner) { result ->
                viewBinding.imageOfNewCategory.setImageResource(result)
                Timber.i("The position: $result")
                categoryImageResource = result
            }
    }

    override fun constructViewBinding(): FragmentNewCategoryBinding =
        FragmentNewCategoryBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentNewCategoryBinding) {
        observeCategoryChange(viewBinding)
        observeActions()

        viewBinding.imageOfNewCategory.setOnClickListener {
            navigateToImagePicker()
        }
        viewBinding.doneNewCategoryBtn.setOnClickListener {
            createNewCategory(viewBinding)
            viewModel.onDoneButtonClick()
        }
    }

    private fun createNewCategory(viewBinding: FragmentNewCategoryBinding) {
        categoryName = viewBinding.nameOfNewCategory.text.toString()

        viewModel.saveCategory(TransactionCategory(44, categoryName, categoryImageResource))
    }


    override fun setupNavigation() {
        navigation = CreateNewCategoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun navigateToImagePicker() {
        viewModel.navigateToImagePicker()
    }


    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            when (action) {
                is CreateNewCategoryViewModel.Action.ShowData -> {
                    idd = action.list.size
                }
            }
        }
    }
}
