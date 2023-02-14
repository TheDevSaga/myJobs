package com.example.myjobs

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myjobs.modules.authentication.AuthenticationActivity
import com.example.myjobs.modules.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun goToDashboard() {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        fun goToOnboard() {
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.mainStateFlow.collect() { event ->
                when (event) {
                    MainViewModel.MainEvent.Authenticated -> {
                        goToDashboard()
                    }
                    MainViewModel.MainEvent.Initial -> {
                    }
                    MainViewModel.MainEvent.New -> {
                        goToOnboard()
                    }
                }
            }
        }

    }

}