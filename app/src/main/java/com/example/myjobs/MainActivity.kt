package com.example.myjobs

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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


        viewModel.user.observe(this) { user ->
            if (user == null) {
                goToOnboard()
            } else {
                goToDashboard()
            }
        }
    }

}