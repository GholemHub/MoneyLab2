package com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentNewCategoryBinding
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.navigaion.CreateNewCategoryNavigation
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategory.viewmodel.CreateNewCategoryViewModel
import com.gholem.moneylab.features.add.chooseTransactionCategory.createNewCategoryImage.CreateNewCategoryImageFragment.Companion.KEY_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.i

@AndroidEntryPoint
class CreateNewCategoryFragment :
    BaseFragment<FragmentNewCategoryBinding, CreateNewCategoryViewModel>() {

    private val viewModel: CreateNewCategoryViewModel by viewModels()
    lateinit var navigation: CreateNewCategoryNavigation

    private fun observeCategoryChange(viewBinding: FragmentNewCategoryBinding) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            KEY_IMAGE
        )
            ?.observe(viewLifecycleOwner) { result ->
                viewBinding.imageOfNewCategory.setImageResource(result)
            }
    }

    override fun constructViewBinding(): FragmentNewCategoryBinding =
        FragmentNewCategoryBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentNewCategoryBinding) {

        viewBinding.imageOfNewCategory.setOnClickListener {
            navigateToImagePicker()
        }
        observeCategoryChange(viewBinding)
    }

    override fun setupNavigation() {
        navigation = CreateNewCategoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun navigateToImagePicker() {
        viewModel.navigateToImagePicker()
    }
}
