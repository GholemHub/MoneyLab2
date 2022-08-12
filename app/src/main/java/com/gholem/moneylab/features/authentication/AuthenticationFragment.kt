package com.gholem.moneylab.features.authentication

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultCallback
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
import timber.log.Timber.i


@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, AuthenticationViewModel>() {

    private val viewModel: AuthenticationViewModel by viewModels()

    lateinit var authenticationNavigation: AuthenticationNavigation

    override fun constructViewBinding(): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(layoutInflater)

    override fun init(viewBinding: FragmentAuthenticationBinding) {
        verifyLastSignedInAccount()
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

    var getUser = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == Activity.RESULT_OK) {

                val data: Intent? = it.data

                val signInTask: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = signInTask.getResult(ApiException::class.java)
                i("Accont : ${account.email}")

                if (signInTask.isSuccessful) {
                    viewModel.goToDashboard()
                }
            } else {
                i("Accont Doesnot exist")
            }
        }
    )

    private fun signIn() {
        val signInIntent = getGoogleSignInClient().signInIntent
        getUser.launch(signInIntent)
    }


/*
    private fun onActivityResult(requestCode: Int, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            when (requestCode) {
                RC_SIGN_IN -> {
                    v.launch(intent)
                    i("RC_SIGN_IN: ${result.data}")
                }
            }
        }
    }*/
/*
    private fun signIn() {
        val signInIntent = getGoogleSignInClient().signInIntent
        //startActivityForResult(signInIntent, RC_SIGN_IN)
        resultLauncher.launch(signInIntent)
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        i("!!!!!!!!!!!!!!!! ${result.resultCode}")
        if (result.resultCode == RC_SIGN_IN) {
            // There are no request codes
            val data: Intent? = result.data

        }
    }
*/

    fun verifyLastSignedInAccount() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}