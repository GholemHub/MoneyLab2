package com.gholem.moneylab.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gholem.moneylab.R
import com.gholem.moneylab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var bindingMain: ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        MenuListener()
    }

    private fun MenuListener() {
        bindingMain.bnv?.background = null

        var navigationController = findNavController(R.id.Fragment)
        bindingMain.bnv?.setupWithNavController(navigationController)
    }
}
