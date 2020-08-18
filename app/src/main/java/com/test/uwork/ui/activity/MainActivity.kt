package com.test.uwork.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.test.uwork.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            supportActionBar?.title = when (destination.id) {
                R.id.loginFragment -> getString(R.string.login)
                R.id.picturesFragment -> getString(R.string.pictures)
                else -> getString(R.string.pictures)
            }

        }

        if (navController.currentDestination?.id == R.id.picturesFragment)
            supportActionBar?.title = getString(R.string.pictures)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }


}