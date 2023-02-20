package com.example.myjobs.modules.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myjobs.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavView
        val navController = (supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment).navController
        // Hook your navigation controller to bottom navigation view
        binding.bottomNavView.setupWithNavController(navController)
    }
}