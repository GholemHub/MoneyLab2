package com.gholem.moneylab.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gholem.moneylab.R
import com.gholem.moneylab.common.BottomNavigationVisibilityBus
import com.gholem.moneylab.databinding.ActivityMainBinding
import com.gholem.moneylab.features.add.activity.AddActivity
import dagger.hilt.android.AndroidEntryPoint
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

        showBottomNavigation(savedInstanceState?.getBoolean(BOTTOM_NAVIGATION_VISIBILITY_KEY) ?: false)
    }
    //Can change stuff when screen is rotating(or when screen is reloaded)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BOTTOM_NAVIGATION_VISIBILITY_KEY, binding.bottomNavigationView.isVisible )
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
        binding.bottomAppBar.isVisible = isVisible
        binding.bottomNavigationFab.isVisible = isVisible
    }

    private fun setupFabButton() {
        binding.bottomNavigationFab.setOnClickListener {
            var intent = Intent(this, AddActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
            //binding.bottomNavigationView.selectedItemId = R.id.add_navigation
        }
    }

    companion object {
        private const val BOTTOM_NAVIGATION_VISIBILITY_KEY = "BOTTOM_NAVIGATION_VISIBILITY_KEY"
    }
}
