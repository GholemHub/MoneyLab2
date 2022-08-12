package com.gholem.moneylab.features.authentication

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAuthenticationBinding
import com.gholem.moneylab.features.authentication.navigation.AuthenticationNavigation
import com.gholem.moneylab.features.authentication.viewmodel.AuthenticationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import timber.log.Timber.i


@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, AuthenticationViewModel>() {

    private val viewModel: AuthenticationViewModel by viewModels()

    lateinit var authenticationNavigation: AuthenticationNavigation

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val signInTask: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (signInTask.isSuccessful) {
                    Timber.i("Logged on account: ${signInTask.getResult(ApiException::class.java).email}")
                    viewModel.goToDashboard ()
                } else {
                    Timber.i("Login failed: ${signInTask.exception?.message}")
                }
            }
        }


    override fun constructViewBinding(): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentAuthenticationBinding) {
        viewBinding.GoogleSignInButton.setOnClickListener {
            signIn()
        }
    }

    private fun getGoogleSignInClient() = GoogleSignIn.getClient(
        requireContext(),
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
    )

    override fun setupNavigation() {
        authenticationNavigation = AuthenticationNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, authenticationNavigation::navigate)
    }

    private fun signIn() {
        val signInIntent = getGoogleSignInClient().signInIntent
        signInLauncher.launch(signInIntent)
    }
}