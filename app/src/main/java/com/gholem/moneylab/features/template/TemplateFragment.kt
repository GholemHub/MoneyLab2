package com.gholem.moneylab.features.template

import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentTemplateBinding
import com.gholem.moneylab.features.template.navigation.TemplateNavigation
import com.gholem.moneylab.features.template.viewmodel.TemplateViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TemplateFragment : BaseFragment<FragmentTemplateBinding, TemplateViewModel>() {

    private val viewModel: TemplateViewModel by viewModels()

    lateinit var templateNavigation: TemplateNavigation

    override fun constructViewBinding(): FragmentTemplateBinding =
        FragmentTemplateBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentTemplateBinding) {
        viewModel.getTemplates()
        observeData()
    }

    override fun setupNavigation() {
        templateNavigation = TemplateNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, templateNavigation::navigate)
    }

    private fun observeData() {
        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { uiState ->
            when (uiState) {
                TemplateViewModel.UiState.Empty -> {
                    // Initial state
                }
                TemplateViewModel.UiState.Loading -> {
                    // Show progress bar while loading data
                }
                is TemplateViewModel.UiState.Loaded -> {
                    // Hide progress bar and show data on the UI when loaded
                    uiState.templateModelList
                }
            }
        }
    }
}