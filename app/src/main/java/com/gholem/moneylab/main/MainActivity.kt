package com.gholem.moneylab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gholem.moneylab.R
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber.i
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bottomNavigationVisibilityBus: BottomNavigationVisibilityBus

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        showBottomNavigation(
            savedInstanceState?.getBoolean(BOTTOM_NAVIGATION_VISIBILITY_KEY) ?: false
        )
    }

    //Can change stuff when screen is rotating(or when screen is reloaded)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(
            BOTTOM_NAVIGATION_VISIBILITY_KEY,
            binding.bottomNavigationView.isVisible
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_container) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        bottomNavigationView.background = null

        setupBottomNavigationVisibility()
        setupFabButton()
    }

    private fun setupBottomNavigationVisibility() {
        bottomNavigationVisibilityBus.isVisible.observe(this, ::showBottomNavigation)
    }

    private fun showBottomNavigation(isVisible: Boolean) {
        binding.bottomNavigationFab.isEnabled = false
        binding.bottomAppBar.isVisible = isVisible
        binding.bottomNavigationFab.isVisible = isVisible
        binding.bottomNavigationFab.isEnabled = true
    }

    private fun setupFabButton() {
        binding.bottomNavigationFab.setOnClickListener {
            binding.bottomNavigationView.selectedItemId = R.id.add_navigation
            bottomNavigationVisibilityBus.changeVisibility(false)
        }
    }

    companion object {
        private const val BOTTOM_NAVIGATION_VISIBILITY_KEY = "BOTTOM_NAVIGATION_VISIBILITY_KEY"
    }
}
