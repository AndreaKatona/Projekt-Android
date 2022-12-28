package com.example.a3tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.a3tracker.databinding.ActivityMainBinding
import com.example.a3tracker.manager.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: AppCompatActivity() {

    val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() called!")
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = App.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "EMPTY")
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController : NavController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = if(destination.id == R.id.loginFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        if (token != "EMPTY") {
            if (token != null) {
                Log.d(TAG, "Token: $token")
                navController.navigate(R.id.taskListFragment)

            }

        } else {
            Log.d(TAG, "TOKEN NOT EXISTING")
            navController.navigate(R.id.loginFragment)

        }
        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.activitiesFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.taskListFragment)
                R.id.settingsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment2)

                else ->
                {

                }

            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called!")
    }

}
