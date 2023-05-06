package com.example.cakeforbakers


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.cakeforbakers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setUpActionBar()
    }

    private fun setUpActionBar() {


        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment?
        val navController = navHostFragment!!.navController

        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signUpFragment -> binding.mainBottomNavigation.visibility = View.GONE
                R.id.signInFragment -> binding.mainBottomNavigation.visibility = View.GONE
                else -> binding.mainBottomNavigation.visibility = View.VISIBLE
            }
        }

    }


}