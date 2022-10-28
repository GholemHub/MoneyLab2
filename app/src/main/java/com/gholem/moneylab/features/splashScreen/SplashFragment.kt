package com.gholem.moneylab.features.splashScreen

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.gholem.moneylab.R
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentSplashBinding
import com.gholem.moneylab.domain.model.CategoryItem.IncomeCategoryModel
import com.gholem.moneylab.domain.model.CategoryItem.ExpenseCategoryModel
import com.gholem.moneylab.features.splashScreen.navigation.SplashNavigation
import com.gholem.moneylab.features.splashScreen.viewmodel.SplashViewModel
import com.gholem.moneylab.util.observeWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
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
        viewModel.goToDashboard()
        setDefaultCategories()

    }

    private fun setDefaultCategories() {
        val listOfDefaultCategories = listOf(
            ExpenseCategoryModel(
                getString(R.string.category_others),
                R.drawable.ic_category_other
            ),
            ExpenseCategoryModel(
                getString(R.string.category_transport),
                R.drawable.ic_category_transport
            ),
            ExpenseCategoryModel(
                getString(R.string.category_food),
                R.drawable.ic_category_food
            ),
            ExpenseCategoryModel(
                getString(R.string.category_sport),
                R.drawable.ic_category_sport
            ),
            IncomeCategoryModel(
                getString(R.string.salory),
                R.drawable.attach_money_24px
            )
        )
        viewModel.getCategoriesAndSetDefault(listOfDefaultCategories)
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

    fun googleSignInOptions() {
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account == null) {
            viewModel.goToAuthentication()
        } else {
            viewModel.goToDashboard()
        }
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