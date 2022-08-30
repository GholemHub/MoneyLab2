package com.gholem.moneylab.features.createNewCategory

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentNewCategoryBinding
import com.gholem.moneylab.domain.model.TransactionCategory
import com.gholem.moneylab.features.createNewCategory.navigaion.CreateNewCategoryNavigation
import com.gholem.moneylab.features.createNewCategory.viewmodel.CreateNewCategoryViewModel
import com.gholem.moneylab.features.createNewCategoryImage.CreateNewCategoryImageFragment.Companion.KEY_IMAGE
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.i

@AndroidEntryPoint
class CreateNewCategoryFragment :
    BaseFragment<FragmentNewCategoryBinding, CreateNewCategoryViewModel>() {

    private val viewModel: CreateNewCategoryViewModel by viewModels()
    lateinit var navigation: CreateNewCategoryNavigation

    private var categoryImageResource: Int = 0

    override fun constructViewBinding(): FragmentNewCategoryBinding =
        FragmentNewCategoryBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentNewCategoryBinding) {
        observeCategoryImageChange(viewBinding)

        observeActions()

        viewBinding.imageOfNewCategory.setOnClickListener {
            navigateToImagePicker()
        }
        viewBinding.doneNewCategoryBtn.setOnClickListener {
            createNewCategory(viewBinding)
        }

    }

    override fun setupNavigation() {
        navigation = CreateNewCategoryNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    private fun observeCategoryImageChange(viewBinding: FragmentNewCategoryBinding) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(KEY_IMAGE)
            ?.observe(viewLifecycleOwner) { result ->
                viewBinding.imageOfNewCategory.setImageResource(result)
                categoryImageResource = result
            }
    }

    private fun createNewCategory(viewBinding: FragmentNewCategoryBinding) {
        val categoryName = viewBinding.nameOfNewCategory.text.toString()
        viewModel.saveCategoryAndFinish(TransactionCategory(categoryName, categoryImageResource))
    }

    private fun navigateToImagePicker() {
        viewModel.navigateToImagePicker()
    }

    private fun observeActions() {
        viewModel.actions.observeWithLifecycle(viewLifecycleOwner) { action ->
            i("BTN2.3")
            when (action) {
                is CreateNewCategoryViewModel.Action.ReturnCategoryId -> {
                    i("BTN2")
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        KEY_CATEGORY_CHOOSE,
                        action.id
                    )
                }

            }
        }
    }

    companion object {
        const val KEY_CATEGORY_CHOOSE = "KEY_CATEGORY_CHOOSE"
    }
}
