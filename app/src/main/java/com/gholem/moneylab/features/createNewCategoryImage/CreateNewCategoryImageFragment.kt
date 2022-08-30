package com.gholem.moneylab.features.createNewCategoryImage

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentNewCategoryImageBinding
import com.gholem.moneylab.features.createNewCategoryImage.adapter.CreateNewCategoryImageAdapter
import com.gholem.moneylab.features.createNewCategoryImage.navigation.CreateNewCategoryImageNavigation
import com.gholem.moneylab.features.createNewCategoryImage.viewmodel.CreateNewCategoryImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CreateNewCategoryImageFragment :
    BaseFragment<FragmentNewCategoryImageBinding, CreateNewCategoryImageViewModel>() {

    private val viewModel: CreateNewCategoryImageViewModel by viewModels()
    lateinit var navigation: CreateNewCategoryImageNavigation

    private val dataAdapter: CreateNewCategoryImageAdapter by lazy {
        CreateNewCategoryImageAdapter {imagePickerResult(it)}
    }

    private fun imagePickerResult(imageId: Int){
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            KEY_IMAGE,
            imageId
        )
        viewModel.navigateToPreviousScreen()
    }

    override fun constructViewBinding(): FragmentNewCategoryImageBinding =
        FragmentNewCategoryImageBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentNewCategoryImageBinding) {
        //observeCategoryChange()
        viewBinding.createNewCategoryRecyclerview
            .apply {
                layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
                hasFixedSize()
                this.adapter = dataAdapter
            }
    }

    override fun setupNavigation() {
        navigation = CreateNewCategoryImageNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, navigation::navigate)
    }

    companion object {
        const val KEY_IMAGE = "KEY_IMAGE"
    }
}