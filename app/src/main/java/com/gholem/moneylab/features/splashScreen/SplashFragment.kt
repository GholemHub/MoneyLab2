package com.gholem.moneylab.features.splashScreen

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentSplashBinding
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigation
import com.gholem.moneylab.features.splashScreen.viewmodel.SplashViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    private val viewModel: SplashViewModel by viewModels()

    lateinit var splashNavigation: SplashNavigation

    override fun constructViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentSplashBinding) {
        observeData()
        viewModel.init()
        viewModel.getCategory()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun setupNavigation() {
        splashNavigation = SplashNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, splashNavigation::navigate)
    }

    private fun observeData() {
        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { uiState ->
            when (uiState) {
                SplashViewModel.UiState.Loading -> {
                    getViewBinding().splashProgressBar.isVisible = true
                }
                SplashViewModel.UiState.Loaded -> {
                    getViewBinding().splashProgressBar.isVisible = false
                }
                SplashViewModel.UiState.NavigateToDashboard -> {
                    viewModel.goToDashboard()
                }
            }
        }
    }
}