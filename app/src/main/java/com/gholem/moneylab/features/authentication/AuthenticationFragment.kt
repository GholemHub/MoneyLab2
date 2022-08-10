package com.gholem.moneylab.features.authentication

import android.content.Intent
import androidx.fragment.app.viewModels
import com.gholem.moneylab.arch.base.BaseFragment
import com.gholem.moneylab.databinding.FragmentAuthenticationBinding
import com.gholem.moneylab.features.authentication.navigation.AuthenticationNavigation
import com.gholem.moneylab.features.authentication.viewmodel.AuthenticationViewModel
import com.gholem.moneylab.features.authentication.viewmodel.AuthenticationViewModel.Companion.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, AuthenticationViewModel>() {

    private val viewModel: AuthenticationViewModel by viewModels()

    lateinit var authenticationNavigation: AuthenticationNavigation

    override fun constructViewBinding(): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentAuthenticationBinding) {
        googleSignInOptions()

        viewBinding.GoogleSignInButton.setOnClickListener {
            signIn()

            Timber.i("Gogole sign in")
        }
    }

    override fun setupNavigation() {
        authenticationNavigation = AuthenticationNavigation(navControllerWrapper)
        viewModel.navigation.observe(this, authenticationNavigation::navigate)
    }

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data)

        viewModel.handleSignInResult(task)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun googleSignInOptions() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        GoogleSignIn.getLastSignedInAccount(requireContext())

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }
}