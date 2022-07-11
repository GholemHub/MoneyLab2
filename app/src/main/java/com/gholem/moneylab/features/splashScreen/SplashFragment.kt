package com.gholem.moneylab.features.splashScreen

import android.view.View
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

    override fun constructViewBinding(): ViewBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        observeData()
        viewModel.init()
    }

    override fun setupNavigation() {
        splashNavigation = SplashNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, splashNavigation::navigate)
    }

    private fun observeData() {
        viewModel.uiState.observeWithLifecycle(viewLifecycleOwner) { uiState ->
            when (uiState) {
                SplashViewModel.UiState.Loading -> {
                    getViewBinding().progressBar.visibility = View.VISIBLE

                }
                SplashViewModel.UiState.Loaded -> {
                    getViewBinding().progressBar.visibility = View.GONE
                }
                SplashViewModel.UiState.NavigateToNext -> {
                    viewModel.goToNexScreen()
                }
            }
        }
    }

}